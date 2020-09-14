package com.bestcommerce.merchant.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Merchant {

    private String test;

    @JsonCreator
    public Merchant(@JsonProperty("test") String test) {
        this.test = test;
    }
}