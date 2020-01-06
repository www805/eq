package com.avst.equipmentcontrol.common.conf;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan("com.avst.equipmentcontrol.web")
@EnableAsync //开启异步任务支持
//配置类实现AsyncConfigurer接口并重写getAsyncExecutor方法
public class TaskExecutorConfig implements AsyncConfigurer {
 
    //返回一个ThreadPoolTaskExecutor，这就就获得了一个基于线程池的TaskExecutor
    @Override
    public Executor getAsyncExecutor() {
         ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setCorePoolSize(5);
            taskExecutor.setMaxPoolSize(10);
            taskExecutor.setQueueCapacity(25);
            taskExecutor.initialize();
            return taskExecutor;
    }
 
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
 
}
