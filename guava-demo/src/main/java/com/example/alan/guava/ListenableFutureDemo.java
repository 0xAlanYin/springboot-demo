package com.example.alan.guava;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ListenableFuture 演示
 * <p>
 * java 中的 Future 表示一个异步计算任务，当任务完成时可以得到计算结果。
 * 如果我们希望一旦计算完成就拿到结果展示给用户或者做计算，就必须使用另一个线程不断的查询计算状态，这样会使代码复杂而且效率低下。
 * 使用guava的ListenableFuture可以帮我们检测Future是否完成了，如果完成就会自动调用回调函数，这样可以减少并发程序的复杂度。
 *
 * @author Alan Yin
 * @date 2021/7/30
 */

public class ListenableFutureDemo {

    public static void main(String[] args) {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        // 执行任务
        final ListenableFuture<Integer> listenableFuture = executorService.submit(() -> {
            return taskJob();
        });

        // 任务完成后回调的函数
        final FutureCallback<Integer> futureCallback = new FutureCallback<Integer>() {
            @Override
            public void onSuccess(@Nullable Integer result) {
                System.out.println("任务执行成功（此处可以对任务进行操作）");
                System.out.println("result is:" + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("任务执行失败（此处可以对任务进行操作）");
            }
        };

        // 绑定任务和回调函数
        Futures.addCallback(listenableFuture, futureCallback, executorService);
    }

    private static Integer taskJob() throws InterruptedException {
        System.out.println("任务执行中...");
        TimeUnit.SECONDS.sleep(5);
        return 666;
    }
}
