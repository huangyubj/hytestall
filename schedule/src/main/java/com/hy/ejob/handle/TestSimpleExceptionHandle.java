package com.hy.ejob.handle;

import com.dangdang.ddframe.job.executor.handler.JobExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSimpleExceptionHandle implements JobExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(TestSimpleExceptionHandle.class);
    public void handleException(String jobName, Throwable throwable) {
        logger.error(String.format("Job '%s' exception occur in job processing", jobName), throwable);
        System.out.println("给管理发邮件：【"+jobName+"】任务异常。" + throwable.getMessage());
    }
}
