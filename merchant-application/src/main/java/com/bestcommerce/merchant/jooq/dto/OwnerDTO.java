package com.bestcommerce.merchant.jooq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
