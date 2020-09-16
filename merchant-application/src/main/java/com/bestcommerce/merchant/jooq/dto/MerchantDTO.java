package com.bestcommerce.merchant.jooq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantDTO {
    private int id;
    private OwnerDTO ownerDTO;
    private String type;
    private String name;
    private String password;
}
