
package com.wen.compiler.utils;

import com.wen.compiler.model.SvnModel;
import lombok.Data;

import java.io.IOException;

/**
 * 描述
 *
 * @author Hardy Gao
 * @created 2020/8/9 19:46
 */
@Data
public class JavaWebDeploy {
    private String projectPath;
    private String deployPath;
    private String jdkPath;
    private String tomcatPath;
    private String svnUrl;
    private String userName;
    private String password;
    public void deployFromSvn() throws IOException {
        String svnUrl = this.svnUrl/*"https://192.168.200.1/svn/2017/rd1/sourcecode/fjwarning/message"*/;
        String checkOutPath = this.projectPath;
        String userName = this.userName;
        String password = this.password;
        SvnModel svnModel = new SvnModel();
        svnModel.setSvnUrl(svnUrl);
        svnModel.setDestPath(checkOutPath);
        svnModel.setUserName(userName);
        svnModel.setPassword(password);
        SvnUtil.export(svnModel);
        JavaCompiler javaCompiler = new JavaCompiler();
        javaCompiler.setProjectPath(this.projectPath/*"E:\\gaozw\\cw-project\\styj"*/);
//        javaCompiler.setDeployPath("E:\\gaozw\\cw-compiler\\styj");
        javaCompiler.setDeployPath(this.deployPath/*"H:\\run\\apache-tomcat-8.5.32\\webapps\\styj"*/);
        javaCompiler.setJdkPath(this.jdkPath/*"D:\\run\\java\\jdk1.8.0_172"*/);
        javaCompiler.setTomcatPath(this.tomcatPath/*"H:\\run\\apache-tomcat-8.5.32"*/);
        javaCompiler.deploy();
    }
}
