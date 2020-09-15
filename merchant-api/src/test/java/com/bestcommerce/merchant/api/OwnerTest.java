package com.bestcommerce.merchant.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String JSON =
            "{" +
                    "\"name\":\"farid\"," +
                    "\"email\":\"farid@email.com\"," +
                    "\"phone\":\"5555555\"," +
                    "\"address\":\"Baki , Azerbaijan\"" +
                    "}";

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

}