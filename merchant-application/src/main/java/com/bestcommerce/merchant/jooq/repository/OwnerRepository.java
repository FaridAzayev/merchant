package com.bestcommerce.merchant.jooq.repository;

import com.bestcommerce.merchant.jooq.dto.OwnerDTO;

public interface OwnerRepository {
    OwnerDTO save(OwnerDTO source);
    OwnerDTO getByID(int id);
}
