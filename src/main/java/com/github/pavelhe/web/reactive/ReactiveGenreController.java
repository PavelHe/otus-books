package com.github.pavelhe.web.reactive;

import com.github.pavelhe.model.mongodb.models.*;
import com.github.pavelhe.repository.mongodb.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

@RestController
public class ReactiveGenreController {

    private final ReactiveGenreRepository repository;

    public ReactiveGenreController(ReactiveGenreRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/v2/genre")
    public Flux<Genre> getAll() {
        return repository.findAll();
    }

    @PostMapping(value = "/v2/genre")
    public Mono<Genre> create(@RequestBody Genre genre) {
        return repository.save(genre);
    }

    @GetMapping(value = "/v2/genre/{id}")
    public Mono<ResponseEntity<Genre>> getById(@PathVariable("id") String id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/v2/genre/{id}")
    public Mono<ResponseEntity<Genre>> update(@PathVariable("id") String id, @RequestBody Genre genre) {
        genre.setId(id);
        return repository.save(genre).map(ResponseEntity::ok);
    }

    @DeleteMapping(value = "/v2/genre/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        return repository.findById(id)
                .flatMap(genre -> repository.delete(genre)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
