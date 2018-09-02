package com.github.pavelhe.web.reactive;

import com.github.pavelhe.model.mongodb.models.*;
import com.github.pavelhe.repository.mongodb.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

@RestController
public class ReactiveAuthorController {

    private final ReactiveAuthorRepository repository;

    public ReactiveAuthorController(ReactiveAuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/v2/author")
    public Flux<Author> getAll() {
        return repository.findAll();
    }

    @PostMapping(value = "/v2/author")
    public Mono<Author> create(@RequestBody Author author) {
        return repository.save(author);
    }

    @GetMapping(value = "/v2/author/{id}")
    public Mono<ResponseEntity<Author>> getById(@PathVariable("id") String id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/v2/author/fullname/{name}/{surname}")
    public Mono<ResponseEntity<Author>> getByFullName(@PathVariable("name") String name,
                                                      @PathVariable("surname") String surname) {
        return repository.findByNameAndSurname(name, surname).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/v2/author/{id}")
    public Mono<ResponseEntity<Author>> update(@PathVariable("id") String id, @RequestBody Author author) {
        author.setId(id);
        return repository.save(author).map(ResponseEntity::ok);
    }

    @DeleteMapping(value = "/v2/author/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        return repository.findById(id)
                .flatMap(genre -> repository.delete(genre)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
