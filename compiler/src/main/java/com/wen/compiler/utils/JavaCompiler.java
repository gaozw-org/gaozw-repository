/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.wen.compiler.utils;

import com.google.common.io.Files;
import lombok.Data;
import com.sun.tools.javac.main.Main;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述 传统 JavaWeb 编译
 *
 * @author Hardy Gao
 * @created 2020/8/9 15:56
 */
@Data
public class JavaCompiler {
    private final static Log LOG = LogFactory.getLog(JavaCompiler.class);
    private String projectPath;
    private String tomcatPath;
    private String jdkPath;
    private String deployPath;

    public void deploy() throws IOException {
        LOG.info("==开始编译===");
        compile();
        LOG.info("==编译结束===");
        LOG.info("==开始copy WebRoot===");
        copyWebRootFile();
        LOG.info("==copy WebRoot结束===");
        LOG.info("==开始制作war包===");
        LOG.info("==war包制作完成===");
    }

    /**
     *
     */
    private void copyWebRootFile() throws IOException {
        String classesPath = new File(projectPath, "WebRoot\\WEB-INF\\classes").getAbsolutePath();
        File webRootFile = new File(projectPath, "WebRoot");
        List<File> fileList = loopFile(webRootFile);
        for (File file : fileList) {
            if (file.getAbsolutePath().startsWith(classesPath)) {
                continue;
            }
            File copyToFile = new File(deployPath, file.getAbsolutePath().substring(webRootFile.getAbsolutePath().length() + 1));
            if (!copyToFile.getParentFile().exists()) {
                copyToFile.getParentFile().mkdirs();
            }
            Files.copy(file, copyToFile);
        }
    }

    public void compile() throws IOException {
        String compilePath = deployPath + File.separator + "WEB-INF" + File.separator + "classes";
        File projectSrc = new File(projectPath + File.separator + "src");
        List<File> compileFileList = loopFile(projectSrc);
        List<File> libFileList
                = loopFile(new File(projectPath, "WebRoot" + File.separator + "WEB-INF" + File.separator + "lib"));
        File compileFile = new File(compilePath);
        if (!compileFile.exists()) {
            compileFile.mkdirs();
        }
        List<String> compileJavaList = new ArrayList<>();
        compileJavaList.add("-g:vars");
        compileJavaList.add("-target");
        compileJavaList.add("1.8");
        compileJavaList.add("-Xlint:unchecked");
        compileJavaList.add("-Xlint:deprecation");
        compileJavaList.add("-classpath");
        compileJavaList.add(System.getenv("classpath")
                + jdkPath + File.separator + "src.zip;"
                + compileFile.getAbsolutePath() + ";");
        compileJavaList.add("-bootclasspath");
        StringBuffer sb = new StringBuffer();
        sb.append(jdkPath + "\\jre\\lib\\rt.jar;"
                + jdkPath + "\\jre\\lib\\jce.jar;"
                + jdkPath + "\\jre\\lib\\deploy.jar;");
        for (File libFile : libFileList) {
            if (!libFile.getPath().endsWith(".jar")) {
                continue;
            }
            sb.append(libFile.getAbsolutePath()+ ";");
        }
        compileJavaList.add(sb.toString());
        compileJavaList.add("-d");
        compileJavaList.add(compileFile.getAbsolutePath());
        for (File file : compileFileList) {
            if (!file.getPath().endsWith(".java")) {
                File copyToFile = new File(compileFile, file.getAbsolutePath().substring(projectSrc.getAbsolutePath().length() + 1));
                if (!copyToFile.getParentFile().exists()) {
                    copyToFile.getParentFile().mkdirs();
                }
                Files.copy(file, copyToFile);
                continue;
            }
            compileJavaList.add(file.getAbsolutePath());
        }
        int compileSize = compileJavaList.size();
        String[] compileOptions = new String[compileSize];
        for (int i = 0; i < compileSize; i++) {
            compileOptions[i] = compileJavaList.get(i);
        }
        Main main = new Main("javac");
        main.compile(compileOptions);
    }

    /**
     * 遍历全部文件
     * @param dir
     */
    public List<File> loopFile(File dir) {
        List<File> resultList = new ArrayList<>();
        if (!dir.exists()) {
            return resultList;
        }
        if (dir.isFile()) {
            resultList.add(dir);
            return resultList;
        }
        File[] files = dir.listFiles();
        if (files == null || files.length <= 0) {
            return resultList;
        }
        for (File file : files) {
            if (file.isFile()) {
                resultList.add(file);
                continue;
            }
            List<File> tmpList = loopFile(file);
            if (tmpList != null && tmpList.size() > 0) {
                resultList.addAll(tmpList);
            }
        }
        return resultList;
    }
}
