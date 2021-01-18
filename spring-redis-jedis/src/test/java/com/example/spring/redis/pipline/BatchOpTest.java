package com.example.spring.redis.pipline;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * 需要对Redis进行批量操作时，建议使用 Redis 管道（Pipeline），可以大幅提升性能
 *
 * @author Alan Yin
 * @date 2021/1/13
 */

public class BatchOpTest {

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 6379;

    public static void batchSetNotUsePipelineSet() {
        Jedis jedis = null;
        long startTime;
        try {
            jedis = new Jedis(HOST, PORT);
            startTime = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                jedis.set("testKey" + i, "testValue1");
            }
        } finally {
            jedis.close();
        }
        System.out.println("batchSetNotUsePipeline costTime:" + (System.currentTimeMillis() - startTime));
    }

    public static void batchSetUsePipelineSet() {
        Jedis jedis = null;
        long startTime;
        try {
            jedis = new Jedis(HOST, PORT);
            startTime = System.currentTimeMillis();
            Pipeline pipeline = jedis.pipelined();
            for (int i = 0; i < 100000; i++) {
                pipeline.set("pipeLineKey" + i, "testValue2");
            }
            pipeline.sync();
        } finally {
            jedis.close();
        }
        System.out.println("batchSetUsePipeline costTime:" + (System.currentTimeMillis() - startTime));
    }

    public static void batchSetNotUsePipelineGet() {
        Map<String, String> map = new HashMap<>();
        Jedis jedis = null;
        long startTime;
        try {
            jedis = new Jedis(HOST, PORT);
            startTime = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                jedis.set("testKey" + i, "testValue1");
                map.put("testKey" + i, jedis.get("testKey" + i));
            }
        } finally {
            jedis.close();
        }
        System.out.println("batchSetNotUsePipelineGet costTime:" + (System.currentTimeMillis() - startTime));
    }

    public static void batchSetUsePipelineGet() {
        Map<String, Response<String>> intrmMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        Jedis jedis = null;
        long startTime;
        try {
            jedis = new Jedis(HOST, PORT);
            startTime = System.currentTimeMillis();
            Pipeline pipeline = jedis.pipelined();
            for (int i = 0; i < 100000; i++) {
                intrmMap.put("pipeLineKey" + i, pipeline.get("pipeLineKey" + i));
            }
            pipeline.sync();

            for (Map.Entry<String, Response<String>> entry : intrmMap.entrySet()) {
                map.put(entry.getKey(), entry.getValue().get());
            }
        } finally {
            jedis.close();
        }
        System.out.println("batchSetUsePipelineGet costTime:" + (System.currentTimeMillis() - startTime));
    }

    public static void main(String[] args) {
        // set
//        batchSetNotUsePipelineSet();
//        batchSetUsePipelineSet();

        // get
//        batchSetNotUsePipelineGet();
//        batchSetUsePipelineGet();

    }
}
