package com.alan.demo.validation.config;

import com.alan.demo.validation.service.StudyService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Alan Yin
 * @date 2021/6/8
 */

@Configuration
@ConditionalOnProperty(prefix = "study", name = "enable", havingValue = "true")
public class HelloAutoConfig {

    @Bean
    public StudyService studyService() {
        return new StudyService();
    }
}
