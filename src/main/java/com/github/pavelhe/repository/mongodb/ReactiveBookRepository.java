package com.github.pavelhe.repository.mongodb;


import com.github.pavelhe.model.mongodb.models.*;
import org.springframework.data.mongodb.repository.*;
import reactor.core.publisher.*;

public interface ReactiveBookRepository extends ReactiveMongoRepository<Book, String> {

    Mono<Book> findByName(String name);

}
