package com.hy.provider.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@EnableDubbo(scanBasePackages = "com.hy.provider.service")
//@PropertySource("classpath:/spring/dubbo-provider.properties")
@ImportResource(value = {"classpath:dubbo-provider.xml"})
public class ProviderConfiguration {

}
