package com.example.demo.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "demo-topic", groupId = "demo-group")
    public void listen(String message) {
        System.out.println("Получено сообщение из Kafka: " + message);
    }
}
