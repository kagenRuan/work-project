package com.ruan.yuanyuan.lock.demo;

public class DataNode {

    private volatile boolean shouldRun = false;

    private NameNodeGroupOfferService offerService;

    //初始化方法
    private void initialize(){
        this.shouldRun=true;
        this.offerService= new NameNodeGroupOfferService();
        this.offerService.start();
    }

    //运行方法
    private void run(){
        try {
            while (shouldRun){
              Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DataNode dataNode = new DataNode();
        dataNode.initialize();
        dataNode.run();
    }
}
