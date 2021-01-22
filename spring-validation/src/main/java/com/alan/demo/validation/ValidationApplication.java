package com.alan.demo.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Alan Yin
 * @date 2020/12/29
 */
@SpringBootApplication
// 若不想使用自动化配置，则排除即可
//@SpringBootApplication(exclude = {CustomAutoConfiguration.class})
public class ValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidationApplication.class, args);
    }
}
