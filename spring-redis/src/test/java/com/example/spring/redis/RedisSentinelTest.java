package com.example.spring.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Protocol;

import java.util.HashSet;
import java.util.Set;

class RedisSentinelTest {

    /**
     * redis 哨兵集群测试
     *
     */
    public static void main(String[] args) {
        Set<String> sentinelSet = new HashSet<>();
        sentinelSet.add("127.0.0.1:26379");
        sentinelSet.add("127.0.0.1:26380");
        sentinelSet.add("127.0.0.1:26381");

        String masterName = "mymaster";
        JedisSentinelPool sentinelPool = new JedisSentinelPool(masterName, sentinelSet, new GenericObjectPoolConfig(),
                10000, 10000, null, Protocol.DEFAULT_DATABASE);
        sentinelPool.getResource().set("hello", "world");
        System.out.println(sentinelPool.getResource().get("hello"));
    }

}
