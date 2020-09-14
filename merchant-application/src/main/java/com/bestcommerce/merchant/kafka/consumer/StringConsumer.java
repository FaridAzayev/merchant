package com.bestcommerce.merchant.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StringConsumer implements Consumer {

    @Override
    @KafkaListener(topics = "${app.kafka.consumer-topic}")
    public void consume(String message) {
        log.info("New message: {}", message);
    }

}
