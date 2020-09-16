package com.bestcommerce.merchant.jooq.mapper;

import com.bestcommerce.merchant.jooq.Tables;
import com.bestcommerce.merchant.jooq.dto.MerchantDTO;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class MerchantRecordMapper implements RecordMapper<Record, MerchantDTO> {
    @Override
    public MerchantDTO map(Record record) {
        return MerchantDTO.builder()
                .id(record.get(Tables.MERCHANT.ID))
                .name(record.get(Tables.MERCHANT.NAME))
                .type(record.get(Tables.MERCHANT.TYPE))
                .password(record.get(Tables.MERCHANT.PASSWORD_HASH))
//                .ownerDTO(record.get(Tables.MERCHANT.ID))
                .build();
    }
}