package com.github.pavelhe.web.rest;

import java.net.*;
import java.util.*;

import com.github.pavelhe.model.*;
import com.github.pavelhe.repository.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthorRestController {

    private AuthorRepository authorRepository;

    public AuthorRestController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping(value = "/rest/author")
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @GetMapping(value = "/rest/author/{id}")
    public ResponseEntity<Author> getById(@PathVariable("id") Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/rest/author/fullname/{name}/{surname}")
    public ResponseEntity<Author> getByFullName(@PathVariable("name") String name,
                                                @PathVariable("surname") String surname) {
        Optional<Author> author = authorRepository.findByNameAndSurname(name, surname);
        return author.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/rest/author")
    public ResponseEntity<Author> create(@RequestBody Author author) throws URISyntaxException {
        Author result = authorRepository.save(author);
        return ResponseEntity.created(new URI("/rest/author/" + result.getId())).body(result);
    }

    @PutMapping(value = "/rest/author/{id}")
    public ResponseEntity<Author> update(@PathVariable Long id, @RequestBody Author author) {
        author.setId(id);
        Author result = authorRepository.save(author);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/rest/author/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        authorRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
