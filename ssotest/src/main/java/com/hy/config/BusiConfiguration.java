package com.hy.config;

import com.hy.filter.SsoFilter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.text.SimpleDateFormat;

@Configuration
public class BusiConfiguration {
    @Bean
    public FilterRegistrationBean sessionFilterRegistration(SsoFilter ssoFilter){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(ssoFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("sessionFilter");
        return registration;
    }


    @Bean
    public SsoFilter sessionFilter(){
        SsoFilter sessionFilter = new SsoFilter();
        return sessionFilter;
    }
}
