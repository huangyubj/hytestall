package com.hy.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hy.quartz")
public class QuartzApp {
    public static void main(String[] args) {
        SpringApplication.run(QuartzApp.class);
    }
}
