package com.example.alan.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

/**
 * 消息监听Consumer: 可以建议多个消费者
 *
 * @author Alan Yin
 * @date 2021/1/22
 */
@Component
@Slf4j
public class KafkaListener {

    @org.springframework.kafka.annotation.KafkaListener(id = "myGroup", topics = "test_default_topic")
    public void listen(String input) {
        log.info("input msg value:{}", input);
    }

    @org.springframework.kafka.annotation.KafkaListener(topics = "topic1")
    public void listenT1(ConsumerRecord<?, ?> cr) throws Exception {
        System.out.println("listenT1收到消息！   topic:>>>  " + cr.topic() + "    key:>>  " + cr.key() + "    value:>>  " + cr.value());
    }

    @org.springframework.kafka.annotation.KafkaListener(topics = "topic2")
    public void listenT2(ConsumerRecord<?, ?> cr) throws Exception {
        System.out.println("listenT2收到消息！   topic:>>>  " + cr.topic() + "    key:>>  " + cr.key() + "    value:>>  " + cr.value());
    }
}
