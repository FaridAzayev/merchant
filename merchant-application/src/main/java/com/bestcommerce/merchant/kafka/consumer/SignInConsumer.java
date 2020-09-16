package com.bestcommerce.merchant.kafka.consumer;

import com.bestcommerce.merchant.JwtTokenUtil;
import com.bestcommerce.merchant.api.Merchant;
import com.bestcommerce.merchant.api.SignInRequest;
import com.bestcommerce.merchant.jooq.dto.MerchantDTO;
import com.bestcommerce.merchant.jooq.dto.OwnerDTO;
import com.bestcommerce.merchant.jooq.repository.MerchantRepository;
import com.bestcommerce.merchant.kafka.producer.SignInProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SignInConsumer implements Consumer {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private final MerchantRepository merchantRepository;
    private final SignInProducer signInProducer;

    @Value("${app.kafka.sign-in-producer.topic}")
    private String signInProducerTopic;

    @Autowired
    public SignInConsumer(MerchantRepository merchantRepository,
                          SignInProducer signInProducer) {
        this.merchantRepository = merchantRepository;
        this.signInProducer = signInProducer;
    }


    @Override
    @KafkaListener(topics = "${app.kafka.sign-in-consumer.topic}")
    public void consume(String message) {

        Optional<SignInRequest> signInRequestOptional = getSignIn(message);
        signInRequestOptional.ifPresent(inRequest -> {
            MerchantDTO merchantDTO = merchantRepository.get(inRequest.getName(), inRequest.getPassword());
            log.info("Got {} for in request {}", merchantDTO, inRequest);

            String token = new JwtTokenUtil().generateToken(merchantDTO);
            signInProducer.send(signInProducerTopic, token);
        });
    }

    private Optional<SignInRequest> getSignIn(String message) {
        try {
            SignInRequest inRequest = MAPPER.readValue(message, SignInRequest.class);
            Set<ConstraintViolation<SignInRequest>> violations = VALIDATOR.validate(inRequest);

            if (!violations.isEmpty()) {
                log.error("Got validation {} exceptions: {}", violations.size(),
                        violations.stream()
                                .map(ConstraintViolation::getMessage)
                                .collect(Collectors.joining()));
            }

            return Optional.of(inRequest);
        } catch (JsonProcessingException e) {
            log.error("Got exception while reading Merchant json", e);
            return Optional.empty();
        }
    }


}
