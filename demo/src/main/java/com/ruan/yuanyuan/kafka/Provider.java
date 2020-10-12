package com.ruan.yuanyuan.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName Provider
 * @Author ruanyuanyuan
 * @Date 2020/10/9-09:22
 * @Version 1.0
 * @Description TODO kafka生产端
 **/
public class Provider extends Thread{

    KafkaProducer<Integer,String> producer;
    String topic;

    public Provider(String topic) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"132.232.96.106:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"PRODUCER");
        properties.put(ProducerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG,"15000");
        this.producer = new KafkaProducer<Integer, String>(properties);
        this.topic = topic;
    }


    @Override
    public void run() {
        String message = "kafka message ";
        for (int j = 0; j <20 ; j++) {
            RecordMetadata recordMetadata = null;
            try {
                message= message+j;
                recordMetadata = producer.send(new ProducerRecord<>(topic, message)).get();
                long offset = recordMetadata.offset();
                String topic = recordMetadata.topic();
                System.out.println("消息offset:"+offset+" 消息主题："+topic);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        new Provider("test").start();
    }
}
