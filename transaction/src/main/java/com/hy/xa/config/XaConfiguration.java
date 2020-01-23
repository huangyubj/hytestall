package com.hy.xa.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class XaConfiguration {

    @Autowired
    private Environment environment;

    @Bean("cscDataSource")
    public DataSource getCscDataSource(){
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setXaProperties(build("spring.datasource.druid.cscDB."));
        bean.setXaDataSourceClassName(DruidXADataSource.class.getName());
        bean.setPoolSize(5);
        return bean;
    }

    @Bean("eamDataSource")
    public DataSource getEamDataSource(){
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setXaProperties(build("spring.datasource.druid.eamDB."));
        bean.setXaDataSourceClassName(DruidXADataSource.class.getName());
        bean.setPoolSize(5);
        return bean;
    }

    @Bean("cscJdbcTemplate")
    public JdbcTemplate getCscJdbcTemplate(@Qualifier("cscDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean("eamJdbcTemplate")
    public JdbcTemplate getEamJdbcTemplate(@Qualifier("eamDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    public Properties build(String prefix){
        Properties properties = new Properties();
        properties.setProperty("url", environment.getProperty(prefix+"url"));
        properties.setProperty("username", environment.getProperty(prefix+"username"));
        properties.setProperty("password", environment.getProperty(prefix+"password"));
        return properties;
    }
}
