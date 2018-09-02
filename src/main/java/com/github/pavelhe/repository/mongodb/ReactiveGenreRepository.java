package com.github.pavelhe.repository.mongodb;


import com.github.pavelhe.model.mongodb.models.*;
import org.springframework.data.mongodb.repository.*;
import reactor.core.publisher.*;

public interface ReactiveGenreRepository extends ReactiveMongoRepository<Genre, String> {

    Mono<Genre> findByName(String name);

}
