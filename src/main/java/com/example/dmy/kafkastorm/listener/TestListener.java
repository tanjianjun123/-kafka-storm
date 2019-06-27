package com.example.dmy.kafkastorm.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Author: daimengying
 * @Date: 2018/12/29 12:01
 * @Description:
 */
@Component
public class TestListener {
//    @KafkaListener(topics = {"test"})
//    public void listen(ConsumerRecord<?, ?> record) {
//        System.out.println("kafka的key: " + record.key());
//        System.out.println("kafka的value: " + record.value().toString());
//    }
}
