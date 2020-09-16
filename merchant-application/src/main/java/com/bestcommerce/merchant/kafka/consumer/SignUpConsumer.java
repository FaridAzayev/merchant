package com.bestcommerce.merchant.kafka.consumer;

import com.bestcommerce.merchant.api.Merchant;
import com.bestcommerce.merchant.jooq.dto.MerchantDTO;
import com.bestcommerce.merchant.jooq.dto.OwnerDTO;
import com.bestcommerce.merchant.jooq.repository.MerchantRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SignUpConsumer implements Consumer {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private final MerchantRepository merchantRepository;

    @Autowired
    public SignUpConsumer(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    @KafkaListener(topics = "${app.kafka.sign-up-consumer.topic}")
    public void consume(String message) {

        Optional<Merchant> merchantOptional = getMerchant(message);
        merchantOptional.ifPresent(merchant -> {
            merchantRepository.save(mapToDTO(merchant));
            log.info("{} saved", merchant);
        });
    }

    private Optional<Merchant> getMerchant(String message) {
        try {
            Merchant merchant = MAPPER.readValue(message, Merchant.class);
            Set<ConstraintViolation<Merchant>> violations = VALIDATOR.validate(merchant);

            if (!violations.isEmpty()) {
                log.error("Got validation {} exceptions: {}", violations.size(),
                        violations.stream()
                                .map(ConstraintViolation::getMessage)
                                .collect(Collectors.joining()));
            }

            return Optional.of(merchant);
        } catch (JsonProcessingException e) {
            log.error("Got exception while reading Merchant json", e);
            return Optional.empty();
        }
    }

    private MerchantDTO mapToDTO(Merchant merchant) {
        return MerchantDTO.builder()
                .type(merchant.getType())
                .name(merchant.getName())
                .password(merchant.getPassword())
                .ownerDTO(OwnerDTO.builder()
                        .name(merchant.getOwner().getName())
                        .phone(merchant.getOwner().getPhone())
                        .email(merchant.getOwner().getEmail())
                        .address(merchant.getOwner().getAddress())
                        .build())
                .build();
    }

}
