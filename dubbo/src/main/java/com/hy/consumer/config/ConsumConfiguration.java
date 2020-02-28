package com.hy.consumer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
//@EnableDubbo(scanBasePackages = "com.hy.provider.service")
//@PropertySource("classpath:/spring/dubbo-provider.properties")
@ImportResource(value = {"classpath:dubbo-consumer.xml"})
public class ConsumConfiguration {

}
