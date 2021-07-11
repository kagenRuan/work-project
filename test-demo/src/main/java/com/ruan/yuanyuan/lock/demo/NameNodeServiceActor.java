package com.ruan.yuanyuan.lock.demo;

import java.util.concurrent.CountDownLatch;

/**
 * 负责跟一组NameNode中的某一个NameNode进行通信的组件
 */
public class NameNodeServiceActor {

    public NameNodeServiceActor() {
    }

    /**
     * 向自己负责的那个NameNode进行注册
     */
    public void register(CountDownLatch countDownLatch){
            new RegisterThread(countDownLatch).start();
    }

    //注册线程
    class RegisterThread extends Thread{

        CountDownLatch latch;

        public RegisterThread(CountDownLatch countDownLatch){
            this.latch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("发送请求到NameNode进行注册");
            latch.countDown();;
        }
    }
}
