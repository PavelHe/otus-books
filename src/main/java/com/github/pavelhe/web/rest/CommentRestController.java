package com.github.pavelhe.web.rest;

import java.net.*;
import java.util.*;

import com.github.pavelhe.model.*;
import com.github.pavelhe.repository.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
public class CommentRestController {

    private CommentRepository commentRepository;
    private BookRepository bookRepository;

    public CommentRestController(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "/rest/comment")
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @GetMapping(value = "/rest/comment/{id}")
    public ResponseEntity<Comment> getById(@PathVariable("id") Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/rest/comment/{bookId}")
    public ResponseEntity<Comment> create(@PathVariable String bookId, @RequestBody Comment comment) throws URISyntaxException {
        Optional<Book> bookOptional = bookRepository.findById(Long.valueOf(bookId));
        bookOptional.ifPresent(comment::setBook);
        Comment result = commentRepository.save(comment);
        return ResponseEntity.created(new URI("/rest/comment/" + result.getId())).body(result);
    }

    @PutMapping(value = "/rest/comment/{id}/{bookId}")
    public ResponseEntity<Comment> update(@PathVariable("id") Long id,
                                          @PathVariable("bookId") String bookId,
                                          @RequestBody Comment comment) {
        Optional<Book> bookOptional = bookRepository.findById(Long.valueOf(bookId));
        comment.setId(id);
        bookOptional.ifPresent(comment::setBook);
        Comment result = commentRepository.save(comment);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/rest/comment/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        commentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
