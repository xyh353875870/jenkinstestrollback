//package com.example.demo.httpclientutil;
//
//import com.alibaba.fastjson.JSONObject;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * httpclientutil
// *
// * @author xyh
// * @date 2020-12-30 12:32 下午
// **/
//@Component
//public class HttpClientUtil {
//
//    @Autowired
//    private CloseableHttpClient httpClient;
//    @Autowired
//    private RequestConfig requestConfig;
//
//
//    /**
//     * httpClientConnectionManager post 线程池请求 application/json
//     * @param url        请求url地址
//     * @param headerMap  请求头k-v参数
//     * @param cookie     如果有cookie
//     * @param contentMap 请求报文
//     * @return
//     */
//    public JSONObject doPost(String url, Map<String, String> headerMap, String cookie, String contentMap) {
//
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.setConfig(requestConfig);
//
//        // 添加请求头
//        Iterator<Map.Entry<String, String>> headIterator = headerMap.entrySet().iterator();
//        while (headIterator.hasNext()) {
//            Map.Entry<String, String> next = headIterator.next();
//            httpPost.addHeader(next.getKey(), next.getValue());
//        }
//        // 加入cookie
//        if (StringUtils.isNotBlank(cookie)) {
//            httpPost.setHeader("Cookie", cookie);
//        }
//        try {
//            if (StringUtils.isNotBlank(contentMap)) {
//                StringEntity entity = new StringEntity(contentMap,"utf-8");
//                httpPost.setEntity(entity);
//            }
//            // 执行返回结果
//            CloseableHttpResponse response = httpClient.execute(httpPost);
//            if (response != null && response.getStatusLine().getStatusCode() == 200) {
//                HttpEntity entity = response.getEntity();
//                String result = EntityUtils.toString(entity);
//                return JSONObject.parseObject(result);
//            }
//            httpPost.abort();
//        } catch (Exception e) {
//            httpPost.abort();
//            return null;
//        }
//        return null;
//    }
//}
//
