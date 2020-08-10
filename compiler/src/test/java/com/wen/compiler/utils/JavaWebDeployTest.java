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
 * @created 2020/8/9 19:53
 */
public class JavaWebDeployTest {
    public static void main(String[] args) throws IOException {
        JavaWebDeploy javaWebDeploy = new JavaWebDeploy();
        javaWebDeploy.setSvnUrl("https://192.168.200.1/svn/2017/rd1/sourcecode/fjwarning/styj");
        javaWebDeploy.setUserName("gzw");
        javaWebDeploy.setPassword("gzw2018");
        javaWebDeploy.setProjectPath("H:\\project\\styj");
        javaWebDeploy.setTomcatPath("H:\\run\\apache-tomcat-8.5.32");
        javaWebDeploy.setJdkPath("D:\\run\\java\\jdk1.8.0_172");
        javaWebDeploy.setDeployPath(javaWebDeploy.getTomcatPath() + "\\webapps\\styj");
        javaWebDeploy.deployFromSvn();
    }
}
