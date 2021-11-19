package com.alan.dubbo.demo.provider;

import com.alan.dubbo.api.demo.User;
import com.alan.dubbo.api.demo.UserService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Alan Yin
 * @date 2021/11/1
 */
@DubboService(version = "1.0.0")
public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        System.out.println("UserServiceImpl findById ");
        return new User(1, "bob");
    }

}
