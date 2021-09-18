package com.example.alan.guava.entity;

import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author Alan Yin
 * @date 2021/7/30
 */
@Slf4j
public class Dao {

    Cache<String, List<String>> bizCache = CacheBuilder.newBuilder().build();

    public List<String> getBizList(String bizId) {
        List<String> bizList = null;
        try {
            bizList = bizCache.get(bizId, new Callable<List<String>>() {
                @Override
                public List<String> call() throws Exception {
                    return getBizListFromDB(bizId);
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Optional.fromNullable(bizList).or(Collections.emptyList());
    }

    /**
     * 模拟耗时的数据库读取
     *
     * @param bizId
     * @return
     */
    private List<String> getBizListFromDB(String bizId) {
        log.info("get data from DB");
        List<String> bizList = null;
        try {
            Thread.sleep(6000);
            switch (bizId) {
                case "aaa":
                    bizList = ImmutableList.of("a", "b", "c");
                    break;
                case "bbb":
                    bizList = ImmutableList.of("d", "e", "f");
                    break;
            }
        } catch (InterruptedException e) {
            // print log
        }
        return Optional.fromNullable(bizList).or(Collections.emptyList());
    }

    /**
     * 为什么要把Cache对象放在DAO层？
     * 思考一下Cache的用途，能够想到在一个Application中，对同一种对象的缓存只需要一个就够了。
     * 比如示例中的 bizCache 是缓存对应的业务信息的，那么在这个Application中最好只存在一个 bizCache。
     *
     * 实现这样的要求有以下几种方式：
     * - Spring的Singleton模式（推荐）
     *  比较常见的做法是将Server和DAO都作为单例的bean交给Spring容器管理，而将缓存放在DAO层不但实现了单例，
     *  也更合理（数据的缓存策略本身就应是数据访问层的一部分）。
     * - 静态初始化（不推荐）
     */
}
