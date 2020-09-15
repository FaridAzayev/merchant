package com.bestcommerce.merchant.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void serialize() throws IOException {
        Owner given = new Owner("farid", "farid@email.com", "5555555", "Baki , Azerbaijan");

        String expected = "{" +
                "\"name\":\"farid\"," +
                "\"email\":\"farid@email.com\"," +
                "\"phone\":\"5555555\"," +
                "\"address\":\"Baki , Azerbaijan\"" +
                "}";

        String json = MAPPER.writeValueAsString(given);

        assertThat(json).isEqualTo(expected);
    }

    @Test
    void deserialize() throws IOException {
        String given = "{" +
                "\"name\":\"farid\"," +
                "\"email\":\"farid@email.com\"," +
                "\"phone\":\"5555555\"," +
                "\"address\":\"Baki , Azerbaijan\"" +
                "}";

        Owner expected = new Owner("farid", "farid@email.com", "5555555", "Baki , Azerbaijan");

        var actual = MAPPER.readValue(given, Owner.class);


        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldFailValidation() throws IOException {
        Owner given = new Owner(null, "faridemail.com", "5555555", "");
        List<String> expectedViolations = List.of("Owner address should not be empty",
                "Owner email field has to be in correct format",
                "Owner name should not be empty");

        Set<ConstraintViolation<Owner>> violations = validator.validate(given);
        List<String> violationMessages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

        assertThat(violations)
                .isNotEmpty()
                .hasSize(3);

        assertThat(violationMessages).containsAll(expectedViolations);
    }


}