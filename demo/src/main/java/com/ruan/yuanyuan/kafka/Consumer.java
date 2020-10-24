package com.ruan.yuanyuan.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @ClassName Consumer
 * @Author ruanyuanyuan
 * @Date 2020/10/9-09:23
 * @Version 1.0
 * @Description TODO kafka消费端
 **/
public class Consumer extends Thread{

    KafkaConsumer<Integer,String> consumer;
    String topic;


    public Consumer(String topic) {
        Properties properties = new Properties();
        //设置连接kafka服务端
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"132.232.96.106:9092");
        //对数据进行反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG,"CONSUMER");//声明消费者
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"ID_GROUP");//指定分组
        //session的超时事件
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"30000");
        //自动提交事件
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
        //自动提交确认位置
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //自定义消费者消费哪个putition

        this.consumer = new KafkaConsumer<Integer,String>(properties);
        this.topic = topic;
    }

    @Override
    public void run() {
        consumer.subscribe(Collections.singleton(topic));//订阅主题
        while (true){
            ConsumerRecords<Integer, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));//从主题中获取数据
            consumerRecords.forEach(obj ->{
                System.out.println("从"+obj.topic()+"中获取值为："+obj.value());
            });
        }
    }

    public static void main(String[] args) {
        new Consumer("test_topic_partition").start();
    }
}
