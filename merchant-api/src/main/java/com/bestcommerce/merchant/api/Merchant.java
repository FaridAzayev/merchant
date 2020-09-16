package com.bestcommerce.merchant.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@EqualsAndHashCode
@ToString
public class Merchant {

    @NotNull(message = "Owner field should not be null")
    @ToString.Exclude
    private final Owner owner;

    @NotEmpty(message = "Merchant type should not be empty or null ")
    private final String type;

    @NotEmpty(message = "Merchant name should not be empty or null ")
    private final String name;

    @Pattern(regexp = "[a-zA-Z0-9]{6}[a-zA-Z0-9]*", message = "Merchant password should be at least 6 alfa-numeric character")
    @ToString.Exclude
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