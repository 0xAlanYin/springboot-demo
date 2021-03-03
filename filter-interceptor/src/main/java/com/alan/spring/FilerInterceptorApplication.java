package com.alan.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class FilerInterceptorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilerInterceptorApplication.class, args);
    }

}
