package com.github.pavelhe.repository.mongodb;


import com.github.pavelhe.model.mongodb.models.*;
import org.springframework.data.mongodb.repository.*;
import reactor.core.publisher.*;

public interface ReactiveCommentRepository extends ReactiveMongoRepository<Comment, String> {

    Mono<Comment> findByText(String text);

}
