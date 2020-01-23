package com.hy.ejob.job;


import com.cxytiandi.elasticjob.annotation.ElasticJobConf;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
@ElasticJobConf(name = "simpleEJob", cron = "0/2 * * * * ?",
        shardingItemParameters = "0=beijing|shanghai|tianjin,1=shanghai", shardingTotalCount = 2,//分片
        listener = "com.hy.ejob.handle.TestSimpleListener", jobExceptionHandler = "com.hy.ejob.handle.TestSimpleExceptionHandle"
        )
public class TestSimpleJob implements SimpleJob {
    public void execute(ShardingContext shardingContext) {
//        System.err.println(shardingContext.toString());
        System.out.println(String.format("【%s】执行，参数【%s】", shardingContext.getJobName(), shardingContext.getShardingParameter()));
    }
}
