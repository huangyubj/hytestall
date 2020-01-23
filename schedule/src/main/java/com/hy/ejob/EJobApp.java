package com.hy.ejob;

import com.cxytiandi.elasticjob.annotation.EnableElasticJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hy.ejob")
@EnableElasticJob
public class EJobApp {
    public static void main(String[] args) {
        SpringApplication.run(EJobApp.class);
    }
}
