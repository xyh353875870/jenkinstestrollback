package com.example.demo.async;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * nio异步非阻塞HttpNioClientConfig
 *
 * @author xyh
 * @date 2021-01-03 8:31 上午
 **/
@Configuration
public class NioHttpClientConfig {

    /**
     * 最大线程数
     */
    private Integer maxTotal = 200;

    /**
     * 最少存活线程数
     */
    private Integer defaultMaxPerRoute = 20;

    /**
     * 连接超时时间(毫秒)
     */
    private Integer connectTimeout = 20000;

    /**
     * 请求超时时间(毫秒)
     */
    private Integer connectionRequestTimeout = 20000;

    /**
     * socket连接超时时间(毫秒)
     */
    private Integer socketTimeout = 20000;

    @Bean(name = "nHttpClientConnectionManager")
    public PoolingNHttpClientConnectionManager getNHttpClientConnectionManager() {

        // 配置io线程
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().
                setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .setSoKeepAlive(true)
                .build();

        // 设置连接池大小
        ConnectingIOReactor ioReactor = null;
        try {
            ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor);
        connManager.setMaxTotal(maxTotal);
        connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

        return connManager;
    }

    @Bean(name = "closeableHttpAsyncClient")
    public CloseableHttpAsyncClient getCloseableHttpAsyncClient(@Qualifier("nHttpClientConnectionManager") PoolingNHttpClientConnectionManager nHttpClientConnectionManager,
                                                                @Qualifier("nRequestConfig") RequestConfig requestConfig) {
        CloseableHttpAsyncClient closeableHttpAsyncClient = HttpAsyncClients.custom()
                .setConnectionManager(nHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
        closeableHttpAsyncClient.start();
        return closeableHttpAsyncClient;
    }

    @Bean(name = "nBuilder")
    public RequestConfig.Builder getBuilder() {
        RequestConfig.Builder builder = RequestConfig.custom();
        return builder
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .setCookieSpec(CookieSpecs.STANDARD_STRICT);
    }

    @Bean(name = "nRequestConfig")
    public RequestConfig getRequestConfig(@Qualifier("nBuilder") RequestConfig.Builder builder) {
        return builder.build();
    }
}
