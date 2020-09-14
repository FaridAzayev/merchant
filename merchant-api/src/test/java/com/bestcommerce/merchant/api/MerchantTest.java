package com.bestcommerce.merchant.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class MerchantTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void serialize() throws IOException {
        Merchant merchant = new Merchant("test-test");

        String json = MAPPER.writeValueAsString(merchant);

        assertThat(json).isEqualTo("test");
    }

    @Test
    void deserialize() throws IOException {
        Merchant merchant = new Merchant("test-test");

        var a = MAPPER.readValue("{\"test\":\"1\"}", Merchant.class);


        assertThat(a).isEqualTo("test");
    }

}