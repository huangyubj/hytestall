package com.hy;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
@MapperScan("com.hy.mapper")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(com.hy.App.class, args);
    }

}