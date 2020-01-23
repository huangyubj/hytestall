package com.hy.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hy.schedule")
public class ScheduleApp {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleApp.class);
    }
}
