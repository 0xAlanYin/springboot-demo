package com.example.alan.guava;

import com.example.alan.guava.entity.Dao;

/**
 * GuavaCache 演示
 *
 * @author Alan Yin
 * @date 2021/7/30
 */

public class GuavaCacheDemo {

    public static void main(String[] args) {
        Dao dao = new Dao();
        for (int i = 0; i < 3; i++) {
            long begin = System.currentTimeMillis();
            System.out.println("current:" + i);
            System.out.println(dao.getBizList("aaa"));
            System.out.println(dao.getBizList("bbb"));
            System.out.println("============= cost time:" + (System.currentTimeMillis() - begin));
        }
    }
}
