package com.hy.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;
import java.util.Set;

public class SimpleJob implements Job {
    private int i;
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        i++;
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Set<Map.Entry<String, Object>> set = jobDataMap.entrySet();
        System.out.println("SimpleJob start --- :" + i + ", thread:" + Thread.currentThread().getName() );
        for(Map.Entry<String, Object> entry : set){
            System.out.println("---key:"+ entry.getKey()+ "---value:"+ entry.getValue());
        }
    }

}
