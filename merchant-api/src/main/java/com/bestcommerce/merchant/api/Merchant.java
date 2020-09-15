package com.bestcommerce.merchant.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Merchant {

    private final Owner owner;
    private final String type;
    private final String name;
    private final String password;

    @JsonCreator
    public Merchant(@JsonProperty("owner") Owner owner,
                    @JsonProperty("type") String type,
                    @JsonProperty("name") String name,
                    @JsonProperty("password") String password) {
        this.owner = owner;
        this.type = type;
        this.name = name;
        this.password = password;
    }
}