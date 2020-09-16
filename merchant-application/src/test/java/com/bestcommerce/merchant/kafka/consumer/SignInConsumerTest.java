package com.bestcommerce.merchant.kafka.consumer;

import com.bestcommerce.merchant.JwtTokenUtil;
import com.bestcommerce.merchant.api.SignInRequest;
import com.bestcommerce.merchant.jooq.dto.MerchantDTO;
import com.bestcommerce.merchant.jooq.repository.MerchantRepository;
import com.bestcommerce.merchant.kafka.producer.SignInProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@EmbeddedKafka
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Disabled
class SignInConsumerTest {

    private static final String TOPIC = "test-top";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    Producer<String, String> producer;

    BlockingQueue<ConsumerRecord<String, String>> records;

    KafkaMessageListenerContainer<String, String> container;

    @Mock
    MerchantRepository merchantRepository;
    @Mock
    SignInProducer signInProducer;
    @Mock
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    @InjectMocks
    SignInConsumer signInConsumer;

    @BeforeEach
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("consumer", "false", embeddedKafkaBroker));
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new StringDeserializer());
        ContainerProperties containerProperties = new ContainerProperties(TOPIC);
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<String, String>) records::add);
        container.start();
        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());

        //producer
        Map<String, Object> producerConfigs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new StringSerializer()).createProducer();

        //mocks
//        merchantRepository = Mockito.mock(MerchantRepository.class);
//        signInProducer = Mockito.mock(SignInProducer.class);
//        jwtTokenUtil = Mockito.mock(JwtTokenUtil.class);


        MerchantDTO merchant =  MerchantDTO.builder()
                .name("name")
                .build();
        given(merchantRepository.get(any(), any())).willReturn(merchant);

        String token = "token";
        given(jwtTokenUtil.generateToken(any(MerchantDTO.class))).willReturn(token);

        signInConsumer = new SignInConsumer(merchantRepository, signInProducer, jwtTokenUtil);

    }

    @AfterEach
    void tearDown() {
        container.stop();
    }

    @Test
    void consume() throws JsonProcessingException, InterruptedException {
        //given
        String given = MAPPER.writeValueAsString(new SignInRequest("name", "pswd"));
        producer.send(new ProducerRecord<>(TOPIC, given));
        producer.flush();



        ArgumentCaptor<String> captor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);
        verify(signInProducer).send(captor1.capture(), captor2.capture());

    }

}