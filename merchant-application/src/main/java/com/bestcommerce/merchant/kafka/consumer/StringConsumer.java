package com.bestcommerce.merchant.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StringConsumer {

    @KafkaListener(topics = "${app.kafka.consumer-topic}", groupId = "merchant")
    public void consume(String message) {
        System.out.println("New message: " + message);
    }
}
