package com.github.pavelhe.web.reactive;

import com.github.pavelhe.model.mongodb.models.*;
import com.github.pavelhe.repository.mongodb.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

@RestController
public class ReactiveBookController {

    private final ReactiveBookRepository repository;

    public ReactiveBookController(ReactiveBookRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/v2/book")
    public Flux<Book> getAll() {
        return repository.findAll();
    }

    @PostMapping(value = "/v2/book")
    public Mono<Book> create(@RequestBody Book book) {
        return repository.save(book);
    }

    @GetMapping(value = "/v2/book/{id}")
    public Mono<ResponseEntity<Book>> getById(@PathVariable("id") String id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/v2/book/{id}")
    public Mono<ResponseEntity<Book>> update(@PathVariable("id") String id, @RequestBody Book book) {
        book.setId(id);
        return repository.save(book).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/v2/book/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        return repository.findById(id)
                .flatMap(book -> repository.delete(book)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
