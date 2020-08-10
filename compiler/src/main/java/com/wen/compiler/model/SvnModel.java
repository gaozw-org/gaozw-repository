/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.wen.compiler.model;

import lombok.Data;
import org.tmatesoft.svn.core.wc.SVNRevision;

/**
 * 描述 SVN 模型
 *
 * @author Hardy Gao
 * @created 2020/8/9 12:49
 */
@Data
public class SvnModel {
    /**
     * SVN 地址
     */
    private String svnUrl;
    /**
     * SVN 用户名
     */
    private String userName;
    /**
     * SVN 密码
     */
    private String password;
    /**
     * SVN checkout 路径
     */
    private String destPath;

    /**
     * 从哪个版本开始check
     */
    private SVNRevision fromVision = SVNRevision.HEAD;

    /**
     * 从哪个版本结束check
     */
    private SVNRevision toVision = SVNRevision.HEAD;
}
