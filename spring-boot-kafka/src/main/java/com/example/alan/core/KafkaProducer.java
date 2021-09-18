package com.example.alan.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟生产者
 * <p>
 * 参考链接：https://www.jianshu.com/p/7a284bf4efc9
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


    /**
     *
     * 1.启动 kafka(for mac)
     * brew services start kafka
     *
     * 2.进入 kafka 安装目录查看当前的 topic
     * cd /usr/local/Cellar/kafka/2.5.0
     * ./bin/kafka-topics -list  --zookeeper localhost:2181
     *
     * 3.命令行创建 topic
     * ./bin/kafka-topics --create -zookeeper localhost:2181 --replication-factor 3 --partitions 3 --topic test_default_topic
     *
     */
}
