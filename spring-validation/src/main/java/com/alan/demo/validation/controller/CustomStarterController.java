package com.alan.demo.validation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import starter.service.MsgService;

/**
 * @author Alan Yin
 * @date 2021/1/18
 */
@RestController
public class CustomStarterController {

    @Autowired
    private MsgService msgService;

    @RequestMapping("/sendMsg")
    public String sendMsg(){
        msgService.sendMsg("测试消息");
        return "";
    }

}
