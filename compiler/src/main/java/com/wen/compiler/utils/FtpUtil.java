package com.wen.compiler.utils;

import com.wen.compiler.model.MyFTPFile;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author Hardy Gao
 * @created 2020/8/11 23:34
 */
public class FtpUtil {
    private static final Log LOG = LogFactory.getLog(FtpUtil.class);
    @Setter
    private String host;
    @Setter
    private int port;
    @Setter
    private String path;
    @Setter
    private String userName;
    @Setter
    private String password;
    @Setter
    private String encoding;

    private FTPClient client;

    public boolean login() {
        try {
            client = new FTPClient();
            client.setControlEncoding(encoding);
            client.enterLocalPassiveMode();
            client.setConnectTimeout(30 * 1000);
            client.connect(host, port);
            client.login(userName, password);
            int replyCode = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                client.disconnect();
                LOG.error("连接失败");
                return false;
            }
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            return true;
        } catch (IOException e) {
            LOG.error("FTP 连接或登录失败", e);

        } finally {
        }
        return false;
    }

    public void upload(String localpath, String remotePath) {
        File file = new File(localpath);
        String fileName = file.getName();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            client.makeDirectory(remotePath);
            client.storeFile(remotePath + "/" + fileName, fileInputStream);
        } catch (FileNotFoundException e) {
            LOG.error("上传文件不存在", e);
        } catch (IOException e) {
            LOG.error("文件上传失败", e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void download(String filePath, String localPath) {
        File file = new File(localPath, filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            client.retrieveFile(filePath, fos);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public boolean cd(String path) {
        try {
            return client.changeWorkingDirectory(path);
        } catch (IOException e) {
            LOG.error("FTP切换目录失败", e);
        }
        return false;
    }

    /**
     * 获取文件列表
     * @param path 远程路径
     * @param onlyChildFile 是否仅查本目录 true 查本目录  否则查询所有子目录
     */
    public List<MyFTPFile> getList(String path, boolean onlyChildFile) {
        List<MyFTPFile> resultList = new ArrayList<>();
        try {
            FTPFile[] files = client.listFiles(path);
            for (FTPFile file : files) {
                MyFTPFile myFTPFile = new MyFTPFile(file);
                if (file.isDirectory()) {
                    if (!onlyChildFile) {
                        List<MyFTPFile> tmpList = getList(path + "/" + file.getName(), onlyChildFile);
                        if (tmpList != null && tmpList.size() > 0) {
                            resultList.addAll(tmpList);
                        }
                    }
                    continue;
                }
                myFTPFile.setFilePath(path + "/" + file.getName());
                resultList.add(myFTPFile);
            }
            return resultList;
        } catch (IOException e) {
            LOG.error("获取" + path + "目录下的文件列表异常", e);
        }
        return null;
    }

    public void close() {
        try {
            client.logout();
            client.disconnect();
        } catch (IOException e) {
            LOG.error("退出登录异常", e);
        }
    }

    public static void main(String[] args) throws IOException {
        FtpUtil ftpUtil = new FtpUtil();
        ftpUtil.setHost("192.168.31.20");
        ftpUtil.setPort(2121);
        ftpUtil.setEncoding("GBK");
        ftpUtil.setUserName("gaozw");
        ftpUtil.setPassword("gzw931647");
        ftpUtil.setPath("/share");
        boolean login = ftpUtil.login();
        if (login) {
            ftpUtil.upload("H:\\unzip\\myweb\\web\\WebRoot\\gradle-3.3-all.zip","/share/mine");
            /*List<MyFTPFile> list = ftpUtil.getList("/share", false);
            for (MyFTPFile file : list) {
                System.out.println(file.getFilePath() + ":" + file.getFtpFile().getName());
                ftpUtil.download(file.getFilePath(), "H:\\ftptest");
            }*/
            ftpUtil.close();
        } else {
            System.out.println("登录失败");
        }
    }
}
