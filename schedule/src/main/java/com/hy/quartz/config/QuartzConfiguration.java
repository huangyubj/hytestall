package com.hy.quartz.config;

import com.hy.quartz.job.SimpleJob;
import com.hy.quartz.job.SimpleService;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

@Configuration
public class QuartzConfiguration {
    @Bean("simpleJob")
    public JobDetailFactoryBean getJobDetailFactoryBean(){
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        bean.setJobClass(SimpleJob.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("time", System.currentTimeMillis());
        bean.setJobDataMap(jobDataMap);
        return bean;
    }

    @Bean("cronJob")
    public MethodInvokingJobDetailFactoryBean getMethodInvokingJobDetailFactoryBean(SimpleService simpleService){
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        bean.setTargetObject(simpleService);
        bean.setTargetMethod("exec");
        bean.setConcurrent(true);
        return bean;
    }

    @Bean("simpleTrigger")
    public SimpleTriggerFactoryBean getSimpleTriggerFactoryBean(JobDetail simpleJob){
        SimpleTriggerFactoryBean bean = new SimpleTriggerFactoryBean();
        bean.setJobDetail(simpleJob);
        bean.setStartDelay(0);
        //指定触发次数
//        bean.setRepeatCount(3000);
        //指定触发间隔
        bean.setRepeatInterval(3000);
        return bean;
    }

    @Bean("cronTrigger")
    public CronTriggerFactoryBean getCronTriggerFactoryBean(JobDetail cronJob){
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(cronJob);
        bean.setCronExpression("0/5 * * * * ?");
        return bean;
    }

    @Bean
    public SchedulerFactoryBean getSchedulerFactoryBean(Trigger simpleTrigger, Trigger cronTrigger){
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setTriggers(simpleTrigger, cronTrigger);
        bean.setStartupDelay(5);
        return bean;
    }
}
