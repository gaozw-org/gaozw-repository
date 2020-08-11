package com.wen.compiler.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

/**
 * 描述 制作war包
 *
 * @author Hardy Gao
 * @created 2020/8/10 22:00
 */
public class WarGenerator {
    public static void main(String[] args) throws IOException {
        String projectPath = "H:\\run\\apache-tomcat-8.5.32\\webapps2\\styj";
        String warPath = "H:\\run\\apache-tomcat-8.5.32\\webapps2\\styj.war";
        FileOutputStream fout = new FileOutputStream(new File(warPath));
        Manifest manifest = new Manifest();

        JarOutputStream jarOutputStream = new JarOutputStream(fout,manifest);
        List<File> list = loopFile(new File(projectPath));
        byte[] buffer = new byte[1024];
        for (File file : list) {
            FileInputStream fis = new FileInputStream(file);
            jarOutputStream.putNextEntry(new ZipEntry(file.getAbsolutePath().substring(projectPath.length() + 1)));
            int len;
            while ((len = fis.read(buffer)) > 0) {
                jarOutputStream.write(buffer, 0, len);
            }
            fis.close();
        }
        jarOutputStream.flush();
        jarOutputStream.close();
    }

    public static List<File> loopFile(File dir) {
        List<File> result = new ArrayList<>();
        if (dir == null) {
            return result;
        }
        if (dir.isFile()) {
            if (!dir.exists()) {
                return result;
            }
            result.add(dir);
            return result;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                result.add(file);
                continue;
            }
            List<File> tmpList = loopFile(file);
            if (tmpList != null && tmpList.size() > 0) {
                result.addAll(tmpList);
            }
        }
        return result;
    }
}
