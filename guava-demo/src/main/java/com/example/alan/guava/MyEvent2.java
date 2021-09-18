package com.example.alan.guava;

import com.example.alan.guava.entity.User;
import com.google.common.eventbus.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alan Yin
 * @date 2021/7/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyEvent2 {

    private User user;

    @Subscribe
    public void handle(MyEvent2 event) {
//        throw new IllegalStateException("MyEvent2 exception");
        System.out.println(Thread.currentThread().getName() + ":" + event.user + " is comming.(Subscribe 2)");
    }
}
