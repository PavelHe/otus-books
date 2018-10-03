package com.github.pavelhe.config;

import javax.sql.*;

import com.github.pavelhe.model.Genre;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.*;
import org.springframework.batch.item.*;
import org.springframework.batch.item.data.*;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.support.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.jdbc.core.*;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Genre, com.github.pavelhe.model.mongodb.models.Genre>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemReader<Genre> reader() {
        JdbcPagingItemReader<Genre> reader = new JdbcPagingItemReader<Genre>();
        final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
        sqlPagingQueryProviderFactoryBean.setSelectClause("select *");
        sqlPagingQueryProviderFactoryBean.setFromClause("from genre");
        sqlPagingQueryProviderFactoryBean.setSortKey("id");
        try {
            reader.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        reader.setDataSource(dataSource);
        reader.setPageSize(3);
        reader.setRowMapper(new BeanPropertyRowMapper<>(Genre.class));
        return reader;
    }

    @Bean
    public ItemProcessor<Genre, com.github.pavelhe.model.mongodb.models.Genre> processor() {
        return genre -> {
            com.github.pavelhe.model.mongodb.models.Genre mongoGenre = new com.github.pavelhe.model.mongodb.models.Genre();
            mongoGenre.setId(String.valueOf(genre.getId()));
            mongoGenre.setName(genre.getName());
            return mongoGenre;
        };
    }

    @Bean
    public MongoItemWriter<com.github.pavelhe.model.mongodb.models.Genre> writer() {
        MongoItemWriter<com.github.pavelhe.model.mongodb.models.Genre> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoOperations);
        writer.setCollection("genres");
        return writer;
    }

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importProductsJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

}
