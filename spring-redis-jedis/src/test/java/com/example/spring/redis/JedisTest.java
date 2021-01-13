package com.example.spring.redis;

import com.example.spring.redis.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 对比性能：com.example.spring.redislettuce.SpringRedisLettuceApplicationTests
 * <p>
 * 并发不高的情况下，Lettuce 相比 Jedis 没有明显差别
 *
 * @author Alan Yin
 * @date 2021/1/7
 */

@SpringBootTest
public class JedisTest {

    @Autowired
    private RedisUtil redisUtil;

    private Logger logger = LoggerFactory.getLogger(JedisTest.class);

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Test
    void testTime() {
        String key = "testKey:1";
        String bigValue = "testValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValuetestValue";
        long startTime = System.currentTimeMillis();
        logger.info("begin:" + startTime);
        redisUtil.setString(0, key, "testValue", 6000000);

        for (int i = 0; i < 10000; i++) {
            redisUtil.setString(0, key, bigValue, 600);
        }
        String testValue = redisUtil.getString(0, key);
        logger.info("testValue:" + testValue);
        logger.info("end:" + (System.currentTimeMillis() - startTime));
    }

}