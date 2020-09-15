package com.bestcommerce.merchant.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Owner {
    private final String name;
    private final String email;
    private final String phone;
    private final String address;

    @JsonCreator
    public Owner(@JsonProperty("name") String name,
                 @JsonProperty("email") String email,
                 @JsonProperty("phone") String phone,
                 @JsonProperty("address") String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
