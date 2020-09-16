package com.bestcommerce.merchant.jooq.repository;

import com.bestcommerce.merchant.DBIntegrationTestBase;
import com.bestcommerce.merchant.jooq.dto.MerchantDTO;
import com.bestcommerce.merchant.jooq.dto.OwnerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


class MerchantRepositoryImplTest extends DBIntegrationTestBase {

    @Autowired
    private MerchantRepository merchantRepository;

    @Test
    void get() {

        MerchantDTO expected = MerchantDTO.builder()
                .id(1)
                .password("test")
                .name("name")
                .ownerDTO(OwnerDTO.builder()
                        .id(1)
                        .name("name")
                        .email("email")
                        .phone("phone")
                        .address("address")
                        .build())
                .type("type")
                .build();

        MerchantDTO actual = merchantRepository.get("name", "test");

        assertThat(actual).isEqualTo(expected);
    }
}