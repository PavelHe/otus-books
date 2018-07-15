package com.github.pavelhe.config;

import javax.sql.*;

import com.github.pavelhe.dao.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.jdbc.*;
import org.springframework.context.annotation.*;
import org.springframework.core.io.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.jdbc.datasource.init.*;
import org.springframework.transaction.*;

@Configuration
@PropertySource(value = "file:src/test/resources/application.properties")
public class TestConfiguration {

    private @Value("#{environment['spring.datasource.url']}")
    String jdbcUrl;
    private @Value("#{environment['spring.datasource.username']}")
    String username;
    private @Value("#{environment['spring.datasource.password']}")
    String password;
    private @Value("#{environment['spring.datasource.driver-class-name']}")
    String driverClassName;

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
    public DataSource dataSource() {
        return DataSourceBuilder.create().driverClassName(driverClassName)
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    public AuthorDao testAuthorDao() throws Exception {
        return new AuthorDaoImpl(jdbcTemplate());
    }

    @Bean
    public GenreDao testGenreDao() throws Exception {
        return new GenreDaoImpl(jdbcTemplate());
    }

    @Bean
    public BookDao testBookDao() throws Exception {
        return new BookDaoImpl(jdbcTemplate());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
