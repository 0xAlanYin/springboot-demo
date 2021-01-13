package com.example.spring.redis.controller;

import com.example.spring.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alan Yin
 * @date 2021/1/5
 */
@RestController
@RequestMapping("/test")
public class RedisTestController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/get")
    public String getValue() {
        redisUtil.setString(0, "testKey", "testValue", 100000);
        String value = redisUtil.getString(0, "testKey");
        return value;
    }

}
