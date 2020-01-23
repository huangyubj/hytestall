package com.hy.busi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("事物基础用法")
@RequestMapping("/transfer")
@RestController
public class BusiController {

    private static final String SQL_1 = "update csc_task set task_name=? where task_id=1006039";
    private static final String SQL_2 = "update csc_task_station set task_name=? where task_id=1006039";

    @Autowired
    @Qualifier("busiJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    @Qualifier("busiTransactionManager")
    private DataSourceTransactionManager transactionManager;


    @ApiOperation("spring注解声明事物")
    @Transactional
    @RequestMapping(value = "/declare", method = RequestMethod.GET)
    public String declareTrans(int num){
        jdbcTemplate.update(SQL_1, num);
        jdbcTemplate.update(SQL_2, num);
        if(num > 100){
            throw new RuntimeException();
        }
        return "success";
    }

    @ApiOperation("spring代码声明事物")
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public String codeTrans(final int num){
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                jdbcTemplate.update(SQL_1, num);
                jdbcTemplate.update(SQL_2, num);
                if (num > 100) {
                    throw new RuntimeException();
                }
                return null;
            }
        });
        return "success";
    }

    @ApiOperation("spring jdbc声明事物")
    @RequestMapping(value = "/jdbc", method = RequestMethod.GET)
    public String jdbcTrans(int num){
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            jdbcTemplate.update(SQL_1, num);
            jdbcTemplate.update(SQL_2, num);
            if(num > 100){
                throw new RuntimeException();
            }
            transactionManager.commit(status);
        }catch (RuntimeException e){
            transactionManager.rollback(status);
            throw e;
        }
        return "success";
    }

}
