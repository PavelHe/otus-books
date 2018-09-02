package com.github.pavelhe.repository.mongodb;


import com.github.pavelhe.model.mongodb.models.*;
import org.springframework.data.mongodb.repository.*;
import reactor.core.publisher.*;

public interface ReactiveAuthorRepository extends ReactiveMongoRepository<Author, String> {

    Mono<Author> findByName(String name);

    Mono<Author> findByNameAndSurname(String name, String surname);

}
