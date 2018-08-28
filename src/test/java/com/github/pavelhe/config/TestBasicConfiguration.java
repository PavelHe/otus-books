package com.github.pavelhe.config;

import javax.sql.*;

import com.fasterxml.jackson.databind.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.jdbc.*;
import org.springframework.context.annotation.*;
import org.springframework.core.io.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.datasource.init.*;

@Configuration
@PropertySource(value = "file:src/test/resources/application.properties")
public class TestBasicConfiguration {

    private @Value("#{environment['spring.datasource.url']}")
    String jdbcUrl;
    private @Value("#{environment['spring.datasource.username']}")
    String username;
    private @Value("#{environment['spring.datasource.password']}")
    String password;
    private @Value("#{environment['spring.datasource.driver-class-name']}")
    String driverClassName;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create().driverClassName(driverClassName)
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    public NamedParameterJdbcOperations jdbcTemplate() throws Exception {
        final DataSource dataSource = dataSource();
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("/schema-test.sql"));
        populator.addScript(new ClassPathResource("/data-test.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
