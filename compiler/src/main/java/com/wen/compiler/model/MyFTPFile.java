package com.wen.compiler.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.net.ftp.FTPFile;

/**
 * 描述
 *
 * @author Hardy Gao
 * @created 2020/8/13 22:46
 */
public class MyFTPFile {
    @Setter
    @Getter
    private String filePath;
    @Getter
    private FTPFile ftpFile;

    public MyFTPFile(FTPFile file) {
        this.ftpFile = file;
    }
}
