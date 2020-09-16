package com.bestcommerce.merchant.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode
@ToString
public class SignInRequest {

    @NotNull(message = "Sing in request must contain Merchant Name")
    private final String name;

    @NotNull(message = "Sing in request must contain password")
    @ToString.Exclude
    private final String password;


    @JsonCreator
    public SignInRequest(@JsonProperty("name") String name,
                         @JsonProperty("password") String password) {
        this.name = name;
        this.password = password;
    }
}
