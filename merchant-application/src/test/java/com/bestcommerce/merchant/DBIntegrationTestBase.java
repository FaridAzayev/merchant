package com.bestcommerce.merchant;

import com.bestcommerce.merchant.initializer.Postgres;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Sql("/db/migration/V1__create_initial_schema.sql")
@SpringBootTest
@ContextConfiguration(initializers = {
        Postgres.Initializer.class
})
@Transactional
public abstract class DBIntegrationTestBase {

    @BeforeAll
    static void init() {
        Postgres.container.start();
        System.out.println("container started " + Postgres.container.getJdbcUrl());
    }

}
