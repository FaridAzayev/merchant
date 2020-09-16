package com.bestcommerce.merchant.jooq.repository;

import com.bestcommerce.merchant.jooq.dto.MerchantDTO;

public interface MerchantRepository {
    void save(MerchantDTO source);

    MerchantDTO get(String name, String password);
}
