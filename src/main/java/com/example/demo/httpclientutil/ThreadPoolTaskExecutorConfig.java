package com.example.demo.httpclientutil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolTaskExecutorConfig
 * ThreadPoolTaskExecutor的配置类，里面的所有参数，应当经过压测得出，并且以配置文件的形式抽离出去
 *
 * @author xyh
 * @date 2020-12-30 6:01 下午
 **/
@Configuration
public class ThreadPoolTaskExecutorConfig {

    @Bean("executor")
    public Executor executor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 此方法返回可用处理器的虚拟机的最大数量; 不小于1
        int core = Runtime.getRuntime().availableProcessors();
        // 设置核心线程数
        executor.setCorePoolSize(core);
        // 设置最大线程数
        executor.setMaxPoolSize(core * 2 + 1);
        // 除核心线程外的线程存活时间(秒)
        executor.setKeepAliveSeconds(3);
        // 如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setQueueCapacity(400);
        // 线程名称前缀
        executor.setThreadNamePrefix("用于说明当前线程是用来作什么的，有助于日志定位");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean("poolExecutor")
    public Executor poolExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 此方法返回可用处理器的虚拟机的最大数量; 不小于1
        int core = Runtime.getRuntime().availableProcessors();
        // 设置核心线程数
        executor.setCorePoolSize(core);
        // 设置最大线程数
        executor.setMaxPoolSize(core * 2 + 1);
        // 除核心线程外的线程存活时间(秒)
        executor.setKeepAliveSeconds(6);
        // 如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setQueueCapacity(400);
        // 线程名称前缀
        executor.setThreadNamePrefix("用于说明当前线程是用来作什么的，有助于日志定位");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
