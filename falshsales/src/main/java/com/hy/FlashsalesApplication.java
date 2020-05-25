package com.hy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hy.dao")
public class FlashsalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlashsalesApplication.class, args);
    }

}

