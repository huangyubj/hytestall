package com.hy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.hy.provider")
public class AppProvider {
    public static void main(String[] args) {
        SpringApplication.run(AppProvider.class, args);
    }
}
