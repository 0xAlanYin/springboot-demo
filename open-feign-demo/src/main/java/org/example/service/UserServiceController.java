package org.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

// 在服务端,创建一个对应的控制器来处理请求:
@RestController
public class UserServiceController {

    private static final Logger log = LoggerFactory.getLogger(UserServiceController.class);

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        log.info("UserServiceController Getting user by id: {}", id);
        // 这里应该是从数据库或其他数据源获取用户信息
        // 这里只是一个简单的示例
        User user = new User();
        user.setId(id);
        user.setName("User " + id);
        user.setEmail("user" + id + "@example.com");
        return user;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        log.info("UserServiceController Creating user: {}", user);
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        return newUser;
    }

}
