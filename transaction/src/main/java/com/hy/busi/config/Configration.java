package com.hy.busi.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
public class Configration {

    @Bean("busiDataSource")
    public DataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://192.168.6.32:3307/hap_csc?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("hap");
        dataSource.setPassword("1");
        return dataSource;
    }
    @Bean("busiJdbcTemplate")
    public JdbcTemplate getJdbcTemplate(@Qualifier("busiDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean("busiTransactionManager")
    public DataSourceTransactionManager getTransactionManager(@Qualifier("busiDataSource")DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate getTransactionTemplate(@Qualifier("busiTransactionManager") PlatformTransactionManager platformTransactionManager){
        return new TransactionTemplate(platformTransactionManager);
    }
}
