package com.example.alan.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟生产者
 *
 * @author Alan Yin
 * @date 2021/1/22
 */
@RestController
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @GetMapping("/send")
    public String send(String topic, String key, String data) {
        kafkaTemplate.send(topic, key, data);
        return "success";
    }

}
