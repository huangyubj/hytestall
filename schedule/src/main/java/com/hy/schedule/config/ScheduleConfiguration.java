package com.hy.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class ScheduleConfiguration {

    //配置线程池---触发器和任务共用的
    @Bean(destroyMethod="shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(10);
    }
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelay = 3000)
    public void test1(){

        System.out.println("1111111，当前时间：" + dateFormat.format(new Date())+",线程号："+Thread.currentThread().getName());
    }

    @Scheduled(cron="0/5 * * * * ?")
    public void test2(){
        System.out.println("任务22222，当前时间：" + dateFormat.format(new Date())+",线程号："+Thread.currentThread().getName());
    }
}
