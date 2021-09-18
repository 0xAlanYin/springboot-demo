package com.example.alan.guava;

import com.example.alan.guava.entity.User;
import com.google.common.eventbus.EventBus;

/**
 * EventBus 演示
 *
 * @author Alan Yin
 * @date 2021/7/30
 */

public class EventBusDemo {

    public static void main(String[] args) {
        testEventBus();
    }

    static EventBus eventBus = new EventBus();

    static {
        // 注册的类会被 Guava 查找有没有订阅的方法（即 @Subscribe 注解标记的类）
        eventBus.register(new MyEvent());
        eventBus.register(new MyEvent2());
    }

    private static void testEventBus() {
        // EventBus
        // SPI + service loader
        // Callback/Listener
        User user = new User(2L, "Alan");
        System.out.println(Thread.currentThread().getName() + ": hello," + user + ".");
        eventBus.post(new MyEvent(user));
        eventBus.post(new MyEvent2(user));
    }
}
