package com.github.pavelhe.config;

import com.github.pavelhe.model.mongodb.models.cascade.*;
import com.github.pavelhe.service.mongodb.*;
import com.github.pavelhe.service.mongodb.interfaces.*;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.*;

import static com.github.pavelhe.config.utils.MongoUtils.startEmbeddedMongodb;

@Configuration
@PropertySource(value = "file:src/test/resources/application.properties")
public class TestMongoConfiguration {

    private @Value("#{environment['spring.data.mongodb.port']}")
    String port;
    private @Value("#{environment['spring.data.mongodb.host']}")
    String host;
    private @Value("#{environment['spring.data.mongodb.database']}")
    String dbName;

    @Bean
    public MongoClient mongo() {
        startEmbeddedMongodb(host, Integer.parseInt(port));
        return new MongoClient(host);
    }

    @Bean
    public MongoOperations mongoTemplate() {
        return new MongoTemplate(mongo(), dbName);
    }

    @Bean
    public GenreService testMongoGenreService() {
        return new GenreServiceMongoImpl(mongoTemplate());
    }

    @Bean
    public BookService testMongoBookService() {
        return new BookServiceMongoImpl(mongoTemplate());
    }

    @Bean
    public AuthorService testMongoAuthorService() {
        return new AuthorServiceMongoImpl(mongoTemplate());
    }

    @Bean
    public CommentService testMongoCommentService() {
        return new CommentServiceMongoImpl(mongoTemplate());
    }

    @Bean
    public CascadingMongoEventListener cascadingMongoEventListener() {
        return new CascadingMongoEventListener();
    }

}
