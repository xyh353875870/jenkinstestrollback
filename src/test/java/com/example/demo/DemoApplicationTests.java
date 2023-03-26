package com.example.demo;

import com.example.demo.async.NioHttpClientUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
class DemoApplicationTests {

//    @Autowired
//    ThreadPoolTaskExecutor executor;
//    @Autowired
//    ThreadPoolTaskExecutor poolExecutor;
//    @Autowired
//    HttpClientUtil httpClientUtil;

    @Autowired
    NioHttpClientUtil nioHttpClientUtil;

    @Test
    void contextLoads() {
    }

//    @Test
//    void test() {
//
//        Map<String, String> httpMap = new HashMap<>();
//        httpMap.put("Accept", "application/json");
//        httpMap.put("Content-Type", "application/json");
//        httpMap.put("Accept-Charset", "utf-8");
//        httpMap.put("contentType", "utf-8");
//
//        MessageBean messageBean = new MessageBean();
//        messageBean.setName("xyh");
//        messageBean.setAge(18);
//        JSONObject jsonObject = httpClientUtil.doPost("https://www.baidu.com", httpMap, null, JSONObject.toJSONString(messageBean));
//        System.out.println("这是百度返回给我的报文" + jsonObject);
//    }

    @Test
    void testNioHttp() {

        Map<String, String> httpMap = new HashMap<>();
        httpMap.put("Accept", "application/json");
        httpMap.put("Content-Type", "application/json");
        httpMap.put("Accept-Charset", "utf-8");
        httpMap.put("contentType", "utf-8");



        nioHttpClientUtil.doPost("http://www.so.com/", httpMap, null, null);

    }

    @Test
    void testNioGet() {


        nioHttpClientUtil.doGet("http://www.baidu1.com/");

    }

    public static void main(String[] args) throws Exception {
        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
        PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
        cm.setMaxTotal(100);
        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(cm).build();
        httpAsyncClient.start();
        String[] urisToGet = {
                "http://www.chinaso.com/",
                "http://www.so.com/",
                "http://www.qq.com/",
        };

        final CountDownLatch latch = new CountDownLatch(urisToGet.length);
        for (final String uri : urisToGet) {
            final HttpGet httpget = new HttpGet(uri);
            httpAsyncClient.execute(httpget, new FutureCallback<HttpResponse>() {

                @Override
                public void completed(final HttpResponse response) {
                    latch.countDown();
                    System.out.println(httpget.getRequestLine() + "->" + response.getStatusLine());
                }

                @Override
                public void failed(final Exception ex) {
                    latch.countDown();
                    System.out.println(httpget.getRequestLine() + "->" + ex);
                }

                @Override
                public void cancelled() {
                    latch.countDown();
                    System.out.println(httpget.getRequestLine() + " cancelled");
                }

            });
        }
        latch.await();
    }

}
