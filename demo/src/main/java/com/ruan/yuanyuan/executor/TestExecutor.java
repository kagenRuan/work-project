package com.ruan.yuanyuan.executor;

import java.util.concurrent.*;

/**
 * @ClassName TestExcutor
 * @Author ruanyuanyuan
 * @Date 2020/8/17-23:14
 * @Version 1.0
 * @Description TODO 测试线程池
 **/
public class TestExecutor {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        for (int i = 0; i < 10; i++) {
//
//        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask task = forkJoinPool.submit(new TestForkJoinPool(5));
        System.out.println(task.get());

        /**
         * @Author: ruanyuanyuan
         * @Date: 2020/8/17 23:16
         * @Description: 手动创建线程池
         * @param args:
         * @return: void
         **/
//        ExecutorService service = new ThreadPoolExecutor(
//                2, //核心线程数(线程池初始化线程数量)
//                3, //最大线程数(线程池最大能创建的线程数量)
//                5L, //空闲时间(当线程池中的线程数量已经达到最大线程数量，并且后面没有任务提交，则会在多少时间内回收线程数量使其线程数量达到核心梳理)
//                TimeUnit.MILLISECONDS,//空闲时间单位(时，分，秒)
//                new LinkedBlockingQueue<Runnable>(10),//队列
//                new  TestRejectedExecutionHandler());//拒绝策略(当线程池中的线程数量已经达到了最大线程数量，并且还有任务提交，那么就会执行拒绝策略)

    }
}
/**
 * @Description: 线程池拒绝策略：需要实现RejectedExecutionHandler接口
 **/
class TestRejectedExecutionHandler implements RejectedExecutionHandler{

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("拒绝策略");
    }
}

class TestForkJoinPool extends RecursiveTask<Integer>{

    private int n;

    public TestForkJoinPool(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if(n <= 1){
            return n;
        }
        TestForkJoinPool testForkJoinPool1 = new TestForkJoinPool(n-1);
        testForkJoinPool1.fork();
        TestForkJoinPool testForkJoinPool2 = new TestForkJoinPool(n-2);
        testForkJoinPool2.fork();
        return testForkJoinPool1.join() + testForkJoinPool2.join();
    }
}