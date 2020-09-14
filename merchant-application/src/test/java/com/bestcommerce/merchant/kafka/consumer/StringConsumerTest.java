package com.bestcommerce.merchant.kafka.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StringConsumerTest {

    @Autowired
    StringConsumer stringConsumer;

    @Test
    void consumer() throws InterruptedException {
        Thread.sleep(10000);
    }
}