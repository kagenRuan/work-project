package com.ruan.yuanyuan.thread.executor;

import java.util.concurrent.*;

/**
 * @ClassName TestExcutorAndSubmit
 * @Author ruanyuanyuan
 * @Date 2020/10/12-08:41
 * @Version 1.0
 * @Description TODO 测试Executor方法和Submit方法
 **/
public class TestExecutorAndSubmit {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {

            }
        });

        executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });

        Future<String> ssss = executorService.submit(new Runnable() {
            @Override
            public void run() {

            }
        }, "ssss");
        ssss.get();
    }
}
