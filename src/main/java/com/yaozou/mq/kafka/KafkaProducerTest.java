package com.yaozou.mq.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * @author yao.zou
 * @version V1.0
 * @description:
 * @date 15:19
 */
public class KafkaProducerTest {
    public static void main(String[] args) {
        KafkaProducerTest producerTest = new KafkaProducerTest();
        producerTest.send();
    }
    private KafkaProducer<String, String> producer;
    public KafkaProducerTest(){
        this.producer = kafkaProducer();
    }

    public void send(){
        int max = 2000;
        for (int i = 0;i<max;i++){
            System.out.println("---"+i);
            final ProducerRecord<String, String> record = new ProducerRecord<>("monitor", "test 111111111111111");
            producer.send(record, (metadata, e) -> {
                if (e != null) {
                    e.printStackTrace();
                    System.out.println("Kafka Send failed for record "+record);
                }
            });
        }

    }

    private KafkaProducer<String, String> kafkaProducer() {
        Properties config = new Properties();
        try {
            config.put("client.id", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException ignored) {
        }
        config.put("bootstrap.servers", "192.168.98.90:9092");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        config.put("acks", "all");
        config.put("zk.connect", "192.168.98.90:2181");

        return new KafkaProducer<>(config);
    }
}
