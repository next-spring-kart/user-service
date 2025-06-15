package com.nextspringkart.userservice.config


import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaRepositories(basePackages = ["com.nextspringkart.userservice.repository"])
@EnableJpaAuditing
@EnableTransactionManagement
class DatabaseConfig {

    @Bean
    fun flywayMigrationStrategy() = FlywayMigrationStrategy { flyway ->
        try {
            flyway.migrate()
        } catch (e: Exception) {
            logger.warn("Flyway migration failed, attempting repair and retry: ${e.message}")
            flyway.repair()
            flyway.migrate()
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(DatabaseConfig::class.java)
    }
}