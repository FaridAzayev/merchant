package com.bestcommerce.merchant.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@EqualsAndHashCode
public class Owner {

    @NotEmpty(message = "Owner name should not be empty")
    private final String name;

    @Email( message = "Owner email field has to be in correct format")
    @NotEmpty(message = "Owner email should not be empty")
    private final String email;

    @NotEmpty(message = "Owner phone should not be empty")
    private final String phone;

    @NotEmpty(message = "Owner address should not be empty")
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
