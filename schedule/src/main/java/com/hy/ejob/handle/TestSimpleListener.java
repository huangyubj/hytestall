package com.hy.ejob.handle;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSimpleListener implements ElasticJobListener {
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        String date = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.out.println(String.format("%s--开始执行任务---》%s",date, shardingContexts.getJobName()));
    }

    public void afterJobExecuted(ShardingContexts shardingContexts) {
        String date = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.out.println(String.format("%s--结束任务---》%s",date, shardingContexts.getJobName()));
    }
}
