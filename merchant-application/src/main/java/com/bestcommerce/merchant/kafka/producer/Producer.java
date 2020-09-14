package com.bestcommerce.merchant.kafka.producer;

public interface Producer {
    void send(String topic, String message);
}
