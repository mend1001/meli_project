package com.marketplace.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.marketplace.infrastructure.adapter.out.sqlite.entity")
@EnableJpaRepositories(basePackages = "com.marketplace.infrastructure.adapter.out.sqlite.repository")
public class PersistenceConfig {}

