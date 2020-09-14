package com.bestcommerce.merchant.kafka.producer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StringProducerTest {


    @Autowired
    StringProducer stringProducer;

    @Test
    void testStringProducer() {
        System.out.println("testStringProducer");
        stringProducer.send("test", "xiyar");
    }

}