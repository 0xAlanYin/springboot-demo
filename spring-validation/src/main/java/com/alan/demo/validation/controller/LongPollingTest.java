package com.alan.demo.validation.controller;

import com.alan.demo.validation.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 长轮询
 * 参考链接：https://www.jianshu.com/p/062c2c6e21da
 *
 * @author Alan Yin
 * @date 2021/6/25
 */
@RestController
@Slf4j
public class LongPollingTest {

    @Autowired
    private TaskService taskService;

    /**
     * 1.阻塞调用
     *
     * @return
     */
    @GetMapping("/blocking/request")
    public ResponseEntity blockingRequest() {
        log.info("接收请求，开始处理");
        ResponseEntity<String> responseEntity = taskService.getResult();
        log.info("接收任务线程完成处理");
        return responseEntity;
    }

    /**
     * 2.Callable异步调用
     */
    @GetMapping("/callable/request")
    public Callable<ResponseEntity<String>> callableRequest() {
        log.info("接收请求，开始处理");
        Callable<ResponseEntity<String>> result = (() -> taskService.getResult());
        log.info("接收任务线程完成处理");
        return result;
    }

    /**
     * 3.DeferredResult
     */
    @GetMapping("/longPolling")
    public DeferredResult<String> deferredResultLongPolling() {
        DeferredResult<String> result = new DeferredResult<>();
        log.info("接收请求，开始处理");
        Thread thread = new Thread(() -> {
            try {
                log.info("任务处理中.....");
                Thread.sleep(10000);
                result.setResult("task finish");
                log.info("任务完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        log.info("接收任务线程完成处理");
        return result;
    }


    final Map<Integer, DeferredResult> deferredResultMap = new ConcurrentHashMap<>();

    @GetMapping("/longPolling2")
    public DeferredResult DeferredResultLongPolling2() {
        DeferredResult deferredResult = new DeferredResult(0L);
        deferredResultMap.put(deferredResult.hashCode(), deferredResult);
        deferredResult.onCompletion(() -> {
            deferredResultMap.remove(deferredResult.hashCode());
            System.out.println("剩余" + deferredResultMap.size() + "个 deferredResult 没有响应");
        });
        return deferredResult;
    }

    @GetMapping("/returningLongPollingValue")
    public void returningLongPollingValue() {
        for (Map.Entry<Integer, DeferredResult> entry : deferredResultMap.entrySet()) {
            entry.getValue().setErrorResult("k1");
        }
    }

}
