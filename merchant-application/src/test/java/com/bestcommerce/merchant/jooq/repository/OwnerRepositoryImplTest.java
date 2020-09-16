package com.bestcommerce.merchant.jooq.repository;

import com.bestcommerce.merchant.DBIntegrationTestBase;
import com.bestcommerce.merchant.jooq.dto.MerchantDTO;
import com.bestcommerce.merchant.jooq.dto.OwnerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


class OwnerRepositoryImplTest extends DBIntegrationTestBase {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void get() {
        OwnerDTO expected = OwnerDTO.builder()
                .id(1)
                .name("name")
                .email("email")
                .phone("phone")
                .address("address")
                .build();

        OwnerDTO actual = ownerRepository.getByID(1);

        assertThat(actual).isEqualTo(expected);
    }
}