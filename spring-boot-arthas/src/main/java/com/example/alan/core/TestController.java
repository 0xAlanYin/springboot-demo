package com.example.alan.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 参考 https://juejin.cn/post/6874594476896518158
 *
 * @author Alan Yin
 * @date 2021/1/22
 */
@RestController
@Slf4j
public class TestController {


    @GetMapping("/t")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("head is:" + request.getHeader("testHead"));
        response.setHeader("testHead", "6666");

        Map<String, String> map = new HashMap<>();
        Random random = new Random();
        int i = random.nextInt(10000);
        map.put("key" + i, "value" + i);
        return "success" + i;
    }

}
