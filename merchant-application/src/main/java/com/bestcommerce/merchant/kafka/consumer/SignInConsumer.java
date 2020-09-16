package com.bestcommerce.merchant.kafka.consumer;

import com.bestcommerce.merchant.JwtTokenUtil;
import com.bestcommerce.merchant.api.SignInRequest;
import com.bestcommerce.merchant.api.SignInResponse;
import com.bestcommerce.merchant.jooq.dto.MerchantDTO;
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
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${app.kafka.sign-in-producer.topic}")
    private String signInProducerTopic;

    @Autowired
    public SignInConsumer(MerchantRepository merchantRepository,
                          SignInProducer signInProducer,
                          JwtTokenUtil jwtTokenUtil) {
        this.merchantRepository = merchantRepository;
        this.signInProducer = signInProducer;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    @KafkaListener(topics = "${app.kafka.sign-in-consumer.topic}")
    public void consume(String message) {

        Optional<SignInRequest> signInRequestOptional = getSignIn(message);
        signInRequestOptional.ifPresent(inRequest -> {
            MerchantDTO merchantDTO = merchantRepository.get(inRequest.getName(), inRequest.getPassword());
            log.info("Got {} for in request {}", merchantDTO, inRequest);

            String token = jwtTokenUtil.generateToken(merchantDTO);

            buildResponse(inRequest, token).ifPresent(
                    response -> signInProducer.send(signInProducerTopic, response)
            );
        });
    }

    private Optional<String> buildResponse(SignInRequest inRequest, String token) {
        try {
            return Optional.of(MAPPER.writeValueAsString(new SignInResponse(inRequest.getName(), token)));
        } catch (JsonProcessingException e) {
            log.error("Got error during serialization of response", e);
            return Optional.empty();
        }
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
