package com.hy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hy")
public class App2 {
    public static void main(String[] args) {
        SpringApplication.run(App2.class);
    }
}
