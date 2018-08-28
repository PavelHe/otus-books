package com.github.pavelhe.config;

import java.io.*;

import com.github.pavelhe.service.mongodb.*;
import com.github.pavelhe.service.mongodb.interfaces.*;
import com.mongodb.*;
import de.flapdoodle.embed.mongo.*;
import de.flapdoodle.embed.mongo.config.*;
import de.flapdoodle.embed.mongo.distribution.*;
import de.flapdoodle.embed.process.runtime.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.*;

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
        IMongoConfig mongoConfig = null;
        try {
            mongoConfig = new MongodConfigBuilder()
                    .version(Version.V3_2_20)
                    .net(new Net(host, Integer.parseInt(port), Network.localhostIsIPv6())).build();
            MongodStarter starter = MongodStarter.getDefaultInstance();
            MongodExecutable mongodExecutable = starter.prepare((IMongodConfig) mongoConfig);
            mongodExecutable.start();
            return new MongoClient(host);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public MongoTemplate mongoTemplate() {
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

}
