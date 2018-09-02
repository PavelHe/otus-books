package com.github.pavelhe.web.reactive;

import com.github.pavelhe.model.mongodb.models.*;
import com.github.pavelhe.repository.mongodb.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

@RestController
public class ReactiveCommentController {

    private final ReactiveCommentRepository commentRepository;
    private final ReactiveBookRepository bookRepository;

    public ReactiveCommentController(ReactiveCommentRepository commentRepository,
                                     ReactiveBookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "/v2/comment")
    public Flux<Comment> getAll() {
        return commentRepository.findAll();
    }

    @GetMapping(value = "/v2/comment/{id}")
    public Mono<ResponseEntity<Comment>> getById(@PathVariable("id") String id) {
        return commentRepository.findById(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/v2/comment/{bookId}")
    public Mono<Comment> create(@PathVariable("bookId") String bookId,
                                @RequestBody Comment comment) {
        Book book = bookRepository.findById(bookId).block();
        comment.setBook(book);
        return commentRepository.save(comment);
    }

    @PutMapping(value = "/v2/comment/{id}/{bookId}")
    public Mono<ResponseEntity<Comment>> update(@PathVariable("id") String commentId,
                                                @PathVariable("bookId") String bookId,
                                                @RequestBody Comment comment) {
        comment.setId(commentId);
        bookRepository.findById(bookId).doAfterSuccessOrError((book, throwable) -> comment.setBook(book));
        return commentRepository.save(comment).map(ResponseEntity::ok);
    }

    @DeleteMapping(value = "/v2/comment/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        return commentRepository.findById(id)
                .flatMap(comment -> commentRepository.delete(comment)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
