/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package pn.wen.creator.controller;

import com.sun.image.codec.jpeg.JPEGCodec;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pn.wen.creator.model.TreeDataModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 描述
 *
 * @author Hardy Gao
 * @created 2020/7/21 21:19
 */
@RestController
@RequestMapping("/data")
public class DataJson {
    /**
     *
     * @return
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public List<TreeDataModel> returnTreeJson(Integer size, String checked, HttpServletRequest request, HttpServletResponse response) {
        if (size == null) {
            size = 20;
        }
        JPEGCodec sd
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<TreeDataModel> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TreeDataModel treeDataModel = new TreeDataModel();
            treeDataModel.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            treeDataModel.setOpen(false);
            treeDataModel.setIsParent(String.valueOf(Boolean.TRUE));
            treeDataModel.setName("节点-" + i);
            treeDataModel.setText("节点-" + i);
            if ("true".equals(checked)) {
                treeDataModel.setChecked(true);
            }
            result.add(treeDataModel);
        }
        return result;
    }
}
