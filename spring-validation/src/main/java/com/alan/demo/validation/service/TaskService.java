package com.alan.demo.validation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Alan Yin
 * @date 2021/6/25
 */
@Service
@Slf4j
public class TaskService {

    public ResponseEntity<String> getResult() {

        log.info("任务开始执行，持续等待中...");
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("任务处理完成");
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
}
