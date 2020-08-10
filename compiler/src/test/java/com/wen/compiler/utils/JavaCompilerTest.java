/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.wen.compiler.utils;

import java.io.IOException;

/**
 * 描述
 *
 * @author Hardy Gao
 * @created 2020/8/9 18:26
 */
public class JavaCompilerTest {
    public static void main(String[] args) throws IOException {
        JavaCompiler javaCompiler = new JavaCompiler();
        javaCompiler.setProjectPath("E:\\gaozw\\cw-project\\styj");
//        javaCompiler.setDeployPath("E:\\gaozw\\cw-compiler\\styj");
        javaCompiler.setDeployPath("H:\\run\\apache-tomcat-8.5.32\\webapps\\styj");
        javaCompiler.setJdkPath("D:\\run\\java\\jdk1.8.0_172");
        javaCompiler.setTomcatPath("H:\\run\\apache-tomcat-8.5.32");
        javaCompiler.deploy();
    }
}
