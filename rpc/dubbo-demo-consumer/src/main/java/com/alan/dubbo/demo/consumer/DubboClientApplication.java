package com.alan.dubbo.demo.consumer;

import com.alan.dubbo.api.demo.Order;
import com.alan.dubbo.api.demo.OrderService;
import com.alan.dubbo.api.demo.User;
import com.alan.dubbo.api.demo.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Alan Yin
 * @date 2021/11/1
 */
@SpringBootApplication
public class DubboClientApplication {

    @DubboReference(version = "1.0.0")
//    @DubboReference(version = "1.0.0",url = "dubbo://127.0.0.1:12345")
    private UserService userService;

    @DubboReference(version = "1.0.0")
    private OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(DubboClientApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            User user = userService.findById(1);
            System.out.println("find user id=1 from server:" + user.getName());
            Order order = orderService.findById(66);
            System.out.println(String.format("find order name=%s, amount=%s", order.getName(), order.getAmount()));
        };
    }
}
