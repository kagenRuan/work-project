package com.ruan.yuanyuan.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @ClassName TestC
 * @Author ruanyuanyuan
 * @Date 2020/10/10-14:50
 * @Version 1.0
 * @Description TODO zookeeper分布式锁实现
 **/
public class TestZookeeperCurator {



    public static void main(String[] args) {
        CuratorFramework curatorFramework  = CuratorFrameworkFactory.builder()
                .connectString("")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
        curatorFramework.start();



    }
}
