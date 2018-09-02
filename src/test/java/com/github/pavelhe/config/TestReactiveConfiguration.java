package com.github.pavelhe.config;

import com.github.pavelhe.model.mongodb.models.cascade.*;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.mongo.embedded.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.config.*;
import org.springframework.data.mongodb.repository.config.*;

import static com.github.pavelhe.config.utils.MongoUtils.startEmbeddedMongodb;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.github.pavelhe.repository.mongodb")
@EnableAutoConfiguration
@AutoConfigureAfter(EmbeddedMongoAutoConfiguration.class)
@PropertySource(value = "file:src/test/resources/application.properties")
public class TestReactiveConfiguration extends AbstractReactiveMongoConfiguration {

    private @Value("#{environment['spring.data.mongodb.port']}")
    String port;
    private @Value("#{environment['spring.data.mongodb.host']}")
    String host;
    private @Value("#{environment['spring.data.mongodb.database']}")
    String dbName;

    @Override
    public MongoClient reactiveMongoClient() {
        startEmbeddedMongodb(host, Integer.parseInt(port));
        return MongoClients.create(String.format("mongodb://localhost:%s", port));
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Bean
    public ReactiveCascadingMongoEventListener reactiveCascadingMongoEventListener() {
        return new ReactiveCascadingMongoEventListener();
    }

}
