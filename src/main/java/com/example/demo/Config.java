package com.example.demo;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@EnableJpaRepositories(enableDefaultTransactions = false)
@Configuration
public class Config {

    @Bean("dataSource")
    public DataSource dataSource(DataSourceProperties properties) {
        HikariDataSource ds = properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        ds.setAutoCommit(false);
        //ds.setReadOnly(true);
        ds.setMaxLifetime(30_000);


//        ds.addDataSourceProperty("cachePrepStmts", true);
//        ds.addDataSourceProperty("prepStmtCacheSize", 250);
//        ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
//        ds.addDataSourceProperty("useServerPrepStmts", true);


//        ds.addDataSourceProperty("rewriteBatchedStatements", true);
//        ds.addDataSourceProperty("cacheResultSetMetadata", "true");
//        ds.addDataSourceProperty("cacheServerConfiguration", true);
        ds.addDataSourceProperty("elideSetAutoCommits", "true");
//        ds.addDataSourceProperty("maintainTimeStats", "false");

        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
