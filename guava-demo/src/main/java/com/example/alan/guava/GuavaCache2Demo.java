package com.example.alan.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * GuavaCache 演示
 * <p>
 * 参考链接：https://albenw.github.io/posts/df42dc84/
 * <p>
 * Guava Cache有两种缓存加载的方式：CacheLoader 和 Callable
 *
 * @author Alan Yin
 * @date 2021/8/2
 */
@Slf4j
public class GuavaCache2Demo {

    // 创建一个 LoadingCache，可以进行简单的缓存配置
    private static LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
            // 最大容量为 100(基于容量进行回收)
            .maximumSize(100)
            // 在指定的过期时间内没有读写，缓存数据即失效
//            .expireAfterAccess(3000, TimeUnit.SECONDS)
            // 在指定的过期时间内没有写入，缓存数据即失效
             .expireAfterWrite(3000, TimeUnit.SECONDS)
            // 在指定的过期时间之后访问时，刷新缓存数据，在刷新任务未完成之前，其他线程返回旧值
            .refreshAfterWrite(60, TimeUnit.SECONDS)
            // key 使用弱引用
            .weakKeys()
            // value 使用弱引用
            .weakValues()
            // 当 Entry 被移除时的监听器: 声明一个监听器，以便缓存项被移除时做一些额外操作
            .removalListener(notification -> log.info("notification={}", JSON.toJSON(notification)))
            // 创建一个 CacheLoader,重写 load 方法：当 get 时缓存不在，则load,放入缓存，并返回
            .build(new CacheLoader<String, String>() {
                // 自动写缓存的方法，必须实现
                @Override
                public String load(String key) throws Exception {
                    return "value-" + key;
                }

                // 异步刷新缓存
                @Override
                public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                    return super.reload(key, oldValue);
                }
            });

    public static void main(String[] args) throws ExecutionException {
        /// 方式1
        String value = loadingCache.get("66");
        System.out.println("value is:" + value);

        /// 方式2: Callable在get时可以指定，效果跟CacheLoader一样，区别就是两者定义的时间点不一样，Callable更加灵活，
        //可以理解为Callable是对CacheLoader的扩展
        String key = "77";
        String value2 = loadingCache.get(key, new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "call-" + key;
            }
        });
        System.out.println("value2 is:" + value);

        /// 其他用法
        // 显式插入
        loadingCache.put("k1", "v1");
        // 显示失效
        loadingCache.invalidate("k1");
        loadingCache.invalidateAll();

        ///
        testCacheInvalidate();
    }

    /**
     * 缓存失效机制：
     * 与失效/缓存刷新相关配置有 expireAfterWrite / expireAfterAccess、refreshAfterWrite 还有 CacheLoader的reload方法
     */
    private static void testCacheInvalidate() {
        // 方式1：expireAfterWrite/expireAfterAccess
        // 方式2：refreshAfterWrite
        // 方式3：异步刷新

        // 小结：
        // expireAfterWrite 是允许一个线程进去load方法，其他线程阻塞等待。
        // refreshAfterWrite 是允许一个线程进去load方法，其他线程返回旧的值。
        // 在上一点基础上做成异步，即回源线程不是请求线程。异步刷新是用线程异步加载数据，期间所有请求返回旧的缓存值。
    }

    /**
     * 通常我们都是 CacheBuilder.refreshAfterWrite(long, TimeUnit)和expireAfterWrite(long, TimeUnit) 同时配置，
     * 并且刷新的时间间隔要比过期的时间间隔短！
     * 这样当较长时间没有被访问的缓存项突然被访问时，会触发过期回收而不是刷新，而热点数据只会触发刷新操作不会触发回收操作。
     */
}
