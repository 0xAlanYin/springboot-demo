package com.example.spring.redislettuce;

import com.example.spring.redislettuce.entity.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class SpringRedisLettuceApplicationTests {

    private Logger logger = LoggerFactory.getLogger(SpringRedisLettuceApplicationTests.class);

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Test
    void testLettuce() {
        String key = "user:1";
        redisTemplate.opsForValue().set(key, new User(1, "yx", 20));
        User user = (User) redisTemplate.opsForValue().get(key);
        logger.info("user:" + user);
    }

    @Test
    void testTime() {
        String key = "testKey:1";
        String bigValue = "testValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValue";
        long startTime = System.currentTimeMillis();
        logger.info("begin:" + startTime);
        redisTemplate.opsForValue().set(key, "testValue");

        for (int i = 0; i < 10000; i++) {
            redisTemplate.opsForValue().set( key, bigValue);
        }
        String testValue = (String) redisTemplate.opsForValue().get(key);
        logger.info("testValue:" + testValue);
        logger.info("end:" + (System.currentTimeMillis() - startTime));
    }


}
