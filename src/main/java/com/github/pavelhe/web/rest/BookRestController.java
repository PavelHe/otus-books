package com.github.pavelhe.web.rest;

import java.net.*;
import java.util.*;

import com.github.pavelhe.model.*;
import com.github.pavelhe.repository.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookRestController {

    private BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "/rest/book")
    public Collection<Book> getAll() {
        return bookRepository.findAll();
    }

    @GetMapping(value = "/rest/book/{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/rest/book")
    public ResponseEntity<Book> create(@RequestBody Book book) throws URISyntaxException {
        Book result = bookRepository.save(book);
        return ResponseEntity.created(new URI("/rest/book/" + result.getId()))
                .body(result);
    }

    @PutMapping(value = "/rest/book/{id}")
    public ResponseEntity<Book> update(@PathVariable("id") Long id, @RequestBody Book book) {
        book.setId(id);
        Book result = bookRepository.save(book);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/rest/book/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
