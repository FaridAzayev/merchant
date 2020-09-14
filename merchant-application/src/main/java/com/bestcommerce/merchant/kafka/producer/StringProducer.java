package com.bestcommerce.merchant.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StringProducer implements Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public StringProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, String message) {
        this.kafkaTemplate.send(topic, message);
    }
}
