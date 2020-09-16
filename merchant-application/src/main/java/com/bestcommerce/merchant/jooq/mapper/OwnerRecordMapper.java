package com.bestcommerce.merchant.jooq.mapper;

import com.bestcommerce.merchant.jooq.Tables;
import com.bestcommerce.merchant.jooq.dto.OwnerDTO;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class OwnerRecordMapper implements RecordMapper<Record, OwnerDTO> {
    @Override
    public OwnerDTO map(Record record) {
        return OwnerDTO.builder()
                .id(record.get(Tables.OWNER.ID))
                .name(record.get(Tables.OWNER.NAME))
                .phone(record.get(Tables.OWNER.PHONE))
                .email(record.get(Tables.OWNER.EMAIL))
                .address(record.get(Tables.OWNER.ADDRESS))
                .build();
    }
}