package com.wen.compiler.utils;

import com.sun.tools.javac.file.ZipArchive;
import org.apache.catalina.startup.HostConfig;
import org.apache.catalina.webresources.JarWarResource;

import java.io.*;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.*;
import java.util.zip.ZipOutputStream;

/**
 * 描述 制作war包
 *
 * @author Hardy Gao
 * @created 2020/8/10 22:00
 */
public class WarGenerator {
    public static void main(String[] args) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("H:\\run\\apache-tomcat-8.5.32\\bin\\startup.bat", null, new File("H:\\run\\apache-tomcat-8.5.32\\bin"));
//        main2(args);
        /*FileInputStream fileInputStream = new FileInputStream(new File("H:\\run\\apache-tomcat-8.5.32\\webapps\\styj\\webpage\\index.html"));
        int len = -1;
        byte[] data = new byte[1024];
        while ((len = fileInputStream.read(data)) > 0) {
            System.out.println(new String(data, "UTF-8"));

        }
        fileInputStream.close();*/
    }
    public static void main2(String[] args) throws IOException {
        String projectPath = "H:\\run\\apache-tomcat-8.5.32\\1123";
        String warPath = "H:\\run\\apache-tomcat-8.5.32\\webapps\\styj.war";
        File warFile = new File(warPath);
        if (warFile.exists()) {
            warFile.delete();
        }
        warFile.createNewFile();
        warFile.setWritable(true);
        warFile.setReadable(true);
        warFile.setExecutable(true);
        FileOutputStream fout = new FileOutputStream(warFile);

        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().putValue("Created-By", "1.8.0_172 (Oracle Corporation)");
        JarOutputStream zipOutputStream = new JarOutputStream(fout, manifest);
        List<File> list = loopFile(new File(projectPath),true);
        byte[] buffer = new byte[1024];
        for (File file : list) {
            String jarPath = file.getAbsolutePath().substring(projectPath.length() + 1);
            //System.out.println("filePath:" + file.getAbsolutePath() + ",jarPath:" + jarPath);
            if (file.isDirectory()) {
                System.out.println("dir-" + "filePath:" + file.getAbsolutePath() + ",jarPath:" + jarPath);
                JarEntry jarEntry = new JarEntry(jarPath + File.separator);
                zipOutputStream.putNextEntry(jarEntry);
                zipOutputStream.closeEntry();
                continue;
            }
            System.out.println("file-" + "filePath:" + file.getAbsolutePath() + ",jarPath:" + jarPath);
            FileInputStream fis = new FileInputStream(file);
            JarEntry jarEntry = new JarEntry(jarPath);
            zipOutputStream.putNextEntry(jarEntry);
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, len);
                zipOutputStream.flush();
            }
            zipOutputStream.closeEntry();
        }
        zipOutputStream.finish();
        zipOutputStream.close();
    }

    public static List<File> loopFile(File dir,boolean rootFlag) {
        List<File> result = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                result.add(file);
                continue;
            }
            result.add(file);
            result.addAll(loopFile(file, false));
        }
        return result;
    }
}
