package com.github.pavelhe.config;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.dao.jdbc.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.*;

@Configuration
public class TestJDBCConfiguration {

    @Autowired
    private TestBasicConfiguration testBasicConfiguration;

    @Bean
    public AuthorDao testAuthorJdbcDao() throws Exception {
        return new AuthorDaoJdbcImpl(testBasicConfiguration.jdbcTemplate());
    }

    @Bean
    public GenreDao testGenreJdbcDao() throws Exception {
        return new GenreDaoJdbcImpl(testBasicConfiguration.jdbcTemplate());
    }

    @Bean
    public BookDao testBookJdbcDao() throws Exception {
        return new BookDaoJdbcImpl(testBasicConfiguration.jdbcTemplate());
    }

    @Bean
    public PlatformTransactionManager testJdbcTransactionManager() {
        return new DataSourceTransactionManager(testBasicConfiguration.dataSource());
    }

}
