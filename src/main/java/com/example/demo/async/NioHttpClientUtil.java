package com.example.demo.async;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * NioHttpClientUtil
 *
 * @author xyh
 * @date 2020-12-30 12:32 下午
 **/
@Component
public class NioHttpClientUtil {

    @Autowired
    private CloseableHttpAsyncClient closeableHttpAsyncClient;

    /**
     * CloseableHttpAsyncClient post 线程池请求 application/json
     *
     * @param url        请求url地址
     * @param headerMap  请求头k-v参数
     * @param cookie     如果有cookie
     * @param contentMap 请求报文
     * @return
     */
    public void doPost(String url, Map<String, String> headerMap, String cookie, String contentMap) {

        HttpPost httpPost = new HttpPost(url);

        // 添加请求头
        Iterator<Map.Entry<String, String>> headIterator = headerMap.entrySet().iterator();
        while (headIterator.hasNext()) {
            Map.Entry<String, String> next = headIterator.next();
            httpPost.addHeader(next.getKey(), next.getValue());
        }
        // 加入cookie
        if (StringUtils.isNotBlank(cookie)) {
            httpPost.setHeader("Cookie", cookie);
        }
        try {
            if (StringUtils.isNotBlank(contentMap)) {
                StringEntity entity = new StringEntity(contentMap, "utf-8");
                httpPost.setEntity(entity);
            }
            // 这里回调线程需要根据自己业务创建模板
            closeableHttpAsyncClient.execute(httpPost, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse response) {
                    if (response != null && response.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = response.getEntity();
                        String result = null;
                        try {
                            result = EntityUtils.toString(entity);
                            System.out.println("请求完成，返回的报文是：" + result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void failed(Exception e) {
                    e.printStackTrace();
                    System.out.println("请求失败，异常报文是：" + e);
                }

                @Override
                public void cancelled() {
                    System.out.println("请求被取消--------");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * CloseableHttpAsyncClient get方式请求，这个方法只是用于测试get，实际使用需要自己添加参数进去
     *
     * @param url        请求url地址
     */
    public void doGet(String url) {

        HttpGet httpGet = new HttpGet(url);

        try {
            // 这里回调线程需要根据自己业务创建模板
            closeableHttpAsyncClient.execute(httpGet, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse response) {
                    System.out.println(httpGet.getRequestLine() + "->" + response.getStatusLine());
                }

                @Override
                public void failed(Exception e) {
                    e.printStackTrace();
                    System.out.println("请求失败，异常报文是：" + e);
                }

                @Override
                public void cancelled() {
                    System.out.println("请求被取消--------");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}

