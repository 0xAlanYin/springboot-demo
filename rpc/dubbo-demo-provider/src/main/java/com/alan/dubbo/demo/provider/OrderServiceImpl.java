package com.alan.dubbo.demo.provider;

import com.alan.dubbo.api.demo.Order;
import com.alan.dubbo.api.demo.OrderService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Alan Yin
 * @date 2021/11/1
 */
@DubboService(version = "1.0.0",tag = "blue",weight = 100)
public class OrderServiceImpl implements OrderService {

    @Override
    public Order findById(int id) {
        System.out.println("OrderServiceImpl findById");
        return new Order(20, "book", 18.8f);
    }
}
