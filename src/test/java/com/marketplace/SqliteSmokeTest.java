package com.marketplace;

import com.marketplace.infrastructure.adapter.out.sqlite.repository.JpaItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SqliteSmokeTest {

    @Autowired
    JpaItemRepository itemRepo;

    @Test
    void shouldLoadSeedDataFromSql() {
        var item = itemRepo.findById("MCO2722163664");
        assertThat(item).isPresent();
        assertThat(item.get().getTitle()).contains("Kit de teclado");
    }
}

