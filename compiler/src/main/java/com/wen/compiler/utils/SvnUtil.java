/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.wen.compiler.utils;

import com.wen.compiler.model.SvnModel;
import org.springframework.ui.Model;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;

/**
 * 描述 SVN 工具
 *
 * @author Hardy Gao
 * @created 2020/8/9 12:37
 */
public class SvnUtil {
    public static void main(String[] args) {
        String svnUrl = "https://192.168.200.1/svn/2017/rd1/sourcecode/fjwarning/message";
        String checkOutPath = "E:\\gaozw\\project\\test-svn3\\message";
        String userName = "gzw";
        String password = "gzw2018";
        SvnModel svnModel = new SvnModel();
        svnModel.setSvnUrl(svnUrl);
        svnModel.setDestPath(checkOutPath);
        svnModel.setUserName(userName);
        svnModel.setPassword(password);
        export(svnModel);
//        update(svnModel);
    }

    /**
     * 连接SVN，返回SVN客户端对象
     * @param svnModel
     */
    public static SVNUpdateClient connect(SvnModel svnModel) {
        String userName = svnModel.getUserName();
        String password = svnModel.getPassword();
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNClientManager outClientManager = SVNClientManager.newInstance(
                (DefaultSVNOptions) options, userName, password);
        SVNUpdateClient updateClient = outClientManager.getUpdateClient();
        return updateClient;
    }

    public static long export(SvnModel svnModel) {
        SVNURL svnRepository;
        String svnUrl = svnModel.getSvnUrl();
        String checkOutPath = svnModel.getDestPath();
        try {
            svnRepository = SVNURL.parseURIEncoded(svnUrl);
            File checkOutFile = new File(checkOutPath);
            if (!checkOutFile.exists()) {
                checkOutFile.mkdirs();
            }
            SVNUpdateClient updateClient = connect(svnModel);
            updateClient.setIgnoreExternals(false);
            return updateClient.doExport(svnRepository,checkOutFile, svnModel.getFromVision(),svnModel.getToVision(),"native", true, SVNDepth.INFINITY);
        } catch (SVNException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    /**
     * svn checkout
     * @param svnModel
     */
    public static long update(SvnModel svnModel) {
        String checkOutPath = svnModel.getDestPath();
        try {
            File checkOutFile = new File(checkOutPath);
            SVNUpdateClient updateClient = connect(svnModel);
            updateClient.setIgnoreExternals(false);
            return updateClient.doUpdate(checkOutFile, svnModel.getToVision(),SVNDepth.INFINITY,false, false);
        } catch (SVNException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    /**
     * svn checkout
     * @param svnModel
     */
    public static long checkout(SvnModel svnModel) {
        SVNURL svnRepository;
        String svnUrl = svnModel.getSvnUrl();
        String checkOutPath = svnModel.getDestPath();
        try {
            svnRepository = SVNURL.parseURIEncoded(svnUrl);
            File checkOutFile = new File(checkOutPath);
            if (!checkOutFile.exists()) {
                checkOutFile.mkdirs();
            }
            SVNUpdateClient updateClient = connect(svnModel);
            updateClient.setIgnoreExternals(false);
            return updateClient.doCheckout(svnRepository, checkOutFile, SVNRevision.HEAD, SVNRevision.HEAD,SVNDepth.INFINITY, false);
        } catch (SVNException e) {
            e.printStackTrace();
        }
        return -1L;
    }
}
