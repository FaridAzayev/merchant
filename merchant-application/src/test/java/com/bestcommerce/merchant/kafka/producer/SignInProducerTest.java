//package com.bestcommerce.merchant.kafka.producer;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//
//@EnableKafka
//@SpringBootTest
//@EmbeddedKafka(
//        partitions = 1,
//        controlledShutdown = false,
//        brokerProperties = {
//                "listeners=PLAINTEXT://localhost:3333",
//                "port=3333"
//        })
//class SignInProducerTest {
//
//    @Autowired
//    KafkaEmbedded kafkaEmbeded;
//
//    @Test
//    void send() {
//    }
//}