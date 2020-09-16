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
public class SignInResponse {

    private final String name;

    private final String token;


    @JsonCreator
    public SignInResponse(@JsonProperty("name") String name,
                          @JsonProperty("token") String token) {
        this.name = name;
        this.token = token;
    }
}
