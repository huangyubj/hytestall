package com.hy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hy.demo.mybatis.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
