package com.ruan.yuanyuan.lock.demo;

import java.util.concurrent.CountDownLatch;

/**
 * 负责跟一组NameNode进行通讯的组件
 */
public class NameNodeGroupOfferService {

    //负责跟主节点通讯的组件
    private NameNodeServiceActor activeServiceActor;
    //负责跟备用节点通讯的组件
    private NameNodeServiceActor standbyServiceActor;

    public NameNodeGroupOfferService() {
        this.activeServiceActor = new NameNodeServiceActor();
        this.standbyServiceActor = new NameNodeServiceActor();
    }

    /**
     * 启动组件
     */
    public void start(){
        //直接使用两个组件分别进行注册向准备两个NameNode节点进行注册
        register();

    }

    private void register() {
        try {
            CountDownLatch latch = new CountDownLatch(2);
            this.activeServiceActor.register(latch);
            this.standbyServiceActor.register(latch);
            latch.await();
            System.out.println("准备NameNode都已经注册完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

