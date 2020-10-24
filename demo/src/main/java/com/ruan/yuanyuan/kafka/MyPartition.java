package com.ruan.yuanyuan.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
/**
 * @ClassName MyPartition
 * @Author ruanyuanyuan
 * @Date 2020/10/22-17:26
 * @Version 1.0
 * @Description TODO 自定义分区策略
 **/
public class MyPartition implements Partitioner {
    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/10/22 17:27
     * @Description: 这个方法的作用就是表示将消息发送到哪个分区
     * @param topic: Topic名称
     * @param key: 指定消息Key
     * @param bytes:
     * @param o1: 消息内容
     * @param bytes1:
     * @param cluster:
     * @return: int
     **/
    @Override
    public int partition(String topic, Object key, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        //获取Topic的所有Partition
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        //如果key为null就随机保存到一个partition
        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        int index = (partitionInfos.size()-1) & hash;
        System.out.println("partition_index:"+index);
        return index;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
