package com.bestcommerce.merchant.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class MerchantTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Owner OWNER = new Owner("farid", "farid@email.com", "5555555", "Baki , Azerbaijan");

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

        String actual = MAPPER.writeValueAsString(given);

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
    }

}