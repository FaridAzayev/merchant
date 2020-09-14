package com.bestcommerce.merchant.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StringProducer implements Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public StringProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topic, String message) {
        log.info("Got new message: {}", message);
        log.info("Going to send message to kafka topic: {}", topic);
        this.kafkaTemplate.send(topic, message);
    }
}
