/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package pn.wen.creator.model;

import lombok.Data;

/**
 * 描述
 *
 * @author Hardy Gao
 * @created 2020/7/21 21:21
 */
@Data
public class TreeDataModel {

    private String id;
    private String value;
    private String text;
    private String name;
    private String isParent;
    private boolean open;
    private String icon;
    private boolean checked;
}
