package com.bestcommerce.merchant.jooq.repository;

import com.bestcommerce.merchant.jooq.Tables;
import com.bestcommerce.merchant.jooq.dto.MerchantDTO;
import com.bestcommerce.merchant.jooq.dto.OwnerDTO;
import com.bestcommerce.merchant.jooq.mapper.MerchantRecordMapper;
import com.bestcommerce.merchant.jooq.tables.records.MerchantRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class MerchantRepositoryImpl implements MerchantRepository {

    private final DSLContext dslContext;
    private final OwnerRepository ownerRepository;

    @Autowired
    public MerchantRepositoryImpl(DSLContext dslContext, OwnerRepository ownerRepository) {
        this.dslContext = dslContext;
        this.ownerRepository = ownerRepository;
    }

    @Override
    @Transactional
    public void save(MerchantDTO source) {
        OwnerDTO ownerDTO = ownerRepository.save(source.getOwnerDTO());

        dslContext.insertInto(Tables.MERCHANT)
                .set(Tables.MERCHANT.NAME, source.getName())
                .set(Tables.MERCHANT.OWNER_ID, ownerDTO.getId())
                .set(Tables.MERCHANT.PASSWORD_HASH, source.getPassword())
                .set(Tables.MERCHANT.TYPE, source.getType())
                .returning()
                .fetchOne();
    }

    @Override
    public MerchantDTO get(String name, String password) {
        Optional<MerchantRecord> recordOptional = dslContext.selectFrom(Tables.MERCHANT)
                .where(Tables.MERCHANT.NAME.eq(name).and(Tables.MERCHANT.PASSWORD_HASH.eq(password)))
                .fetchOptional();

        if(recordOptional.isPresent()){
            MerchantDTO merchantDTO = new MerchantRecordMapper().map(recordOptional.get());
            merchantDTO.setOwnerDTO(ownerRepository.getByID(recordOptional.get().getOwnerId()));
            return merchantDTO;
        }

        return null;
    }
}
