package com.github.pavelhe.web.rest;

import java.net.*;
import java.util.*;

import com.github.pavelhe.model.*;
import com.github.pavelhe.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
public class GenreRestController {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreRestController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping(value = "/rest/genre")
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @GetMapping(value = "/rest/genre/{id}")
    public ResponseEntity<Genre> getById(@PathVariable("id") Long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        return genre.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/rest/genre/name/{name}")
    public ResponseEntity<Genre> getByName(@PathVariable("name") String name) {
        Optional<Genre> genre = genreRepository.findByName(name);
        return genre.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/rest/genre")
    public ResponseEntity<Genre> create(@RequestBody Genre genre) throws URISyntaxException {
        Genre result = genreRepository.save(genre);
        return ResponseEntity.created(new URI("/rest/genre/" + result.getId())).body(genre);
    }

    @PutMapping(value = "/rest/genre/{id}")
    public ResponseEntity<Genre> update(@PathVariable Long id, @RequestBody Genre genre) {
        genre.setId(id);
        Genre result = genreRepository.save(genre);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/rest/genre/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        genreRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
