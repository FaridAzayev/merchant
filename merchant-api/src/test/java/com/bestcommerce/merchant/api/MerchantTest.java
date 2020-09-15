package com.bestcommerce.merchant.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


class MerchantTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static Owner OWNER = new Owner("farid", "farid@email.com", "5555555", "Baki , Azerbaijan");
    private static Validator VALIDATOR;

    @BeforeAll
    static void beforeAll() {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void serialize() throws IOException {
        Merchant given = new Merchant(OWNER, "sale", "test-salesman", "123456");
        String expected = "{" +
                "\"owner\":" +
                "{" +
                    "\"name\":\"farid\"," +
                    "\"email\":\"farid@email.com\"," +
                    "\"phone\":\"5555555\"," +
                    "\"address\":\"Baki , Azerbaijan\"" +
                "}," +
                "\"type\":\"sale\"," +
                "\"name\":\"test-salesman\"," +
                "\"password\":\"123456\"" +
                "}";

        var actual = MAPPER.writeValueAsString(given);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deserialize() throws IOException {
        String given = "{" +
                "\"owner\":" +
                "{" +
                    "\"name\":\"farid\"," +
                    "\"email\":\"farid@email.com\"," +
                    "\"phone\":\"5555555\"," +
                    "\"address\":\"Baki , Azerbaijan\"" +
                "}," +
                "\"type\":\"sale\"," +
                "\"name\":\"test-salesman\"," +
                "\"password\":\"123456\"" +
                "}";
        Merchant expected = new Merchant(OWNER, "sale", "test-salesman", "123456");

        var actual = MAPPER.readValue(given, Merchant.class);

        assertThat(actual).isEqualTo(expected);

        Set<ConstraintViolation<Merchant>> violations = VALIDATOR.validate(actual);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidation() {
        Merchant given = new Merchant(null, "", "test-salesman", "ab345");

        List<String> expectedViolations = List.of("Merchant type should not be empty or null ",
                "Merchant password should be at least 6 alfa-numeric character",
                "Owner field should not be null");

        Set<ConstraintViolation<Merchant>> violations = VALIDATOR.validate(given);
        List<String> violationMessages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

        assertThat(violations)
                .isNotEmpty()
                .hasSize(3);

        assertThat(violationMessages).containsAll(expectedViolations);
    }

}