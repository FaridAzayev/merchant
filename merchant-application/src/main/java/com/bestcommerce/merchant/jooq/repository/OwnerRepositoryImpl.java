package com.bestcommerce.merchant.jooq.repository;

import com.bestcommerce.merchant.jooq.mapper.OwnerRecordMapper;
import com.bestcommerce.merchant.jooq.Tables;
import com.bestcommerce.merchant.jooq.dto.OwnerDTO;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class OwnerRepositoryImpl implements OwnerRepository {

    private final DSLContext dslContext;

    @Autowired
    public OwnerRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    @Transactional
    public OwnerDTO save(OwnerDTO source) {
        return dslContext.insertInto(Tables.OWNER)
                .set(Tables.OWNER.NAME, source.getName())
                .set(Tables.OWNER.EMAIL, source.getEmail())
                .set(Tables.OWNER.PHONE, source.getPhone())
                .set(Tables.OWNER.ADDRESS, source.getAddress())
                .returning()
                .fetchOne()
                .map(new OwnerRecordMapper());
    }

    @Override
    public OwnerDTO getByID(int id) {
        return dslContext.selectFrom(Tables.OWNER)
                .where(Tables.OWNER.ID.eq(id))
                .fetchOne()
                .map(new OwnerRecordMapper());    }
}
