package com.ruan.yuanyuan.线程池;

import java.util.concurrent.*;

/**
 * @author ryy
 * @Title: ${线程池}
 * @Package ${Executor,Executors}
 * 底层主要使用BlockingQueue阻塞队列
 * 线程池的七个参数值：
 *int corePoolSize 核心线程数,
 *int maximumPoolSize 最大线程数，最少为1，必须大于等于1,
 *long keepAliveTime 当空闲时间达到keepAliveTime时间后，会将多余空闲的线程销毁直到只剩下corePoolSize个线程数为止,
 *TimeUnit unit  设置keepAliveTime时间的单位，可以设置为秒，分，时,
 *BlockingQueue<Runnable> workQueue 任务被提交，但是未被执行的任务,
 *ThreadFactory threadFactory 生成线程的工厂,
 *RejectedExecutionHandler handler 拒绝策略(当线程池中的执行线程数量以及达到最大maximumPoolSize数则启动拒绝策略)
 * 拒绝策略：
 *  1.AbortPolicy(默认)直接抛出异常，阻止系统运行
 *  2.CallerRunsPolicy:不会抛弃任务，也不会抛出异常，而是将某些任务回退 时
 *  3.DiscardOldestPolicy:抛弃队列中等待最久的任务，然后把当前任务加入队列中尝试再次提交任务
 *  4.DiscardPolicy:直接丢弃任务，不处理也不抛出异常
 *
 * 线程池底层原理：
 *  当提交执行任务后，首先会由corePoolSize核心线程执行，
 * 但是当执行的线程等于corePoolSize核心线程，如果还有任务则将其加到workQueue队列中，如果执行线程等于corePoolSize核心线程并且workQueue等待队列已满，则会创建新的线程，
 * 而新创建的线程数则等于maximumPoolSize - corePoolSize，如果执行的线程数等于maximumPoolSize最大线程数，还有任务则会启动拒绝策略。但是如果执行的线程数等于maximumPoolSize最大线程数
 * 而后面没有任务后，当空闲时间达到keepAliveTime时间，会将多余空闲的线程销毁直到只剩下corePoolSize个线程数为止。
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        /**
         * 重点建议：根据阿里巴巴开发手册说明，不建议这样创建线程池，因为这样会造成OOM
         * 因为在使用是创建的阻塞队列没有设置大小，也就是说无界队列，如果不设置的话在代码底层那阻塞队列的大小为Integer.MaxValue那就等于21亿多会造成内存泄漏
         */
        Executor executor = Executors.newFixedThreadPool(5);//固定线程数
        //Executor executor = Executors.newSingleThreadExecutor();//单一线程数
        //Executor executor = Executors.newCachedThreadPool();//不固定线程数，带缓存
        /**
         * 建议手动创建线程
         * 而设置maximumPoolSize最大线程数的设置=cup数量+1
         */
        ExecutorService executorService = new ThreadPoolExecutor(2,5,1L, TimeUnit.SECONDS,new LinkedBlockingDeque<>(3),Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardPolicy());
        try{
            for(int i=1;i<=9;i++){
                executorService.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 正在办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
    }
}
