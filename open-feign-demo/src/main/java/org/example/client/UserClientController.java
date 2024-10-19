package org.example.client;

import org.example.service.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// 创建一个控制器来使用Feign客户端
@RestController
@RequestMapping("/client")
public class UserClientController {

    private static final Logger log = LoggerFactory.getLogger(UserClientController.class);
    @Autowired
    private UserClient userClient;

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        log.info("Getting user by id: {}", id);
        return userClient.getUserById(id);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        log.info("Creating user: {}", user);
        return userClient.createUser(user);
    }
}

