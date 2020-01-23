package com.hy.ejob.job;

import com.cxytiandi.elasticjob.annotation.ElasticJobConf;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.List;

//@ElasticJobConf(name = "simpleEJob", cron = "0/2 * * * * ?",
//        shardingItemParameters = "0=beijing|shanghai|tianjin,1=shanghai", shardingTotalCount = 2,//分片
//        listener = "com.hy.ejob.handle.TestSimpleListener", jobExceptionHandler = "com.hy.ejob.handle.TestSimpleExceptionHandle"
//)
public class TestDataflowJob implements DataflowJob {
    public List fetchData(ShardingContext shardingContext) {
        return null;
    }

    public void processData(ShardingContext shardingContext, List list) {

    }
}
