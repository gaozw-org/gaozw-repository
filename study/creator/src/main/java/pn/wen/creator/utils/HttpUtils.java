/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package pn.wen.creator.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * INFO:
 *
 * @author Hardy Gao
 * @created 2019-11-25 22:06:00
 */
public class HttpUtils {
    private final static Log LOG = LogFactory.getLog(HttpUtils.class);
    public static String post(String url, Map<String, Object> postBody, Map<String, String> headers) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        int timeout = 30;
        CloseableHttpClient httpClient ;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeout * 1000)
                    .setConnectionRequestTimeout(timeout * 1000)
                    .setSocketTimeout(timeout * 1000)
                    .build();
            httpPost.setConfig(config);
            boolean jsonType = false;
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                    if ("content-type".equals(entry.getKey().toLowerCase())) {
                        if ("application/json".equals(entry.getValue().toLowerCase())) {
                            jsonType = true;
                        }
                    }
                }
            }
            if (postBody != null && postBody.size() > 0) {
                if (!jsonType) {
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    for (Map.Entry<String, Object> entry : postBody.entrySet()) {
                        nameValuePairs.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                    }
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                }else {
                    httpPost.setEntity(new StringEntity(JSON.toJSONString(postBody)));
                }
            }
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
            String infoMsg = "请求接口：" + url + "\n响应结果：" + result;
            LOG.info(infoMsg);
        } catch (IOException e) {
            LOG.error("请求异常", e);
        }
        return result;
    }
    public static String post(String url, String postBody, Map<String, String> headers) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        int timeout = 30;
        CloseableHttpClient httpClient ;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeout * 1000)
                    .setConnectionRequestTimeout(timeout * 1000)
                    .setSocketTimeout(timeout * 1000)
                    .build();
            httpPost.setConfig(config);
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            if (StringUtils.isNotBlank(postBody)) {
                httpPost.setEntity(new StringEntity(JSON.toJSONString(postBody), "UTF-8"));
            }
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            String infoMsg = "请求接口：" + url + "\n响应结果：" + result;
            LOG.info(infoMsg);
        } catch (IOException e) {
            LOG.error("请求异常", e);
            result = "异常：" + e.getMessage();
        }
        return result;
    }
    public static String get(String url, String params) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        if (StringUtils.isNotBlank(params)) {
            url += "?" + params;
        }
        int timeout = 30;
        CloseableHttpClient httpClient ;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("MyName", "gzw");
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeout * 1000)
                    .setConnectionRequestTimeout(timeout * 1000)
                    .setSocketTimeout(timeout * 1000)
                    .build();
            httpGet.setConfig(config);
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
            LOG.info("请求接口：" + url + "\n响应结果：" + result);
        } catch (Exception e) {
            LOG.error("请求异常", e);
        }
        return result;
    }
    /*public static void main(String[] args) {
        String url = "http://127.0.0.1:6060/styjfbsd/abilityApi/getNumberOfPeopleByPosition";
        String params = "longitude=123&latitude=334&testFlag=Y";
//        HttpUtils.get(url, params);
        Map<String, Object> postBody = new HashMap<String, Object>();
        postBody.put("longitude", "123123");
        postBody.put("latitude", "3345677");
        postBody.put("testFlag", "N");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cc", "12");
        map.put("dd", "556g");
        postBody.put("Laga", map);
        Map<String, String> headers = new HashMap<String, String>();
        //headers.put("Content-Type", "application/json");//Content-Type", "application/json
        HttpUtils.post(url, postBody, headers);
    }*/
}
