package com.github.pavelhe.web.reactvie;


import com.github.pavelhe.model.mongodb.models.*;
import com.github.pavelhe.repository.mongodb.*;
import com.github.pavelhe.web.reactive.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.test.web.reactive.server.*;
import reactor.core.publisher.*;

import static org.junit.Assert.*;

public class TestReactiveCommentController extends AbstractReactiveControllerTestClass {

    @Autowired
    private ReactiveCommentRepository commentRepository;
    @Autowired
    private ReactiveBookRepository bookRepository;

    @Test
    public void testGetAll() throws Exception {
        Comment comment = commentRepository.save(mockComment()).block();
        assertNotNull(comment.getId());

        client.get().uri("/v2/comment")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Genre.class);
    }

    @Test
    public void testSave() {
        Comment comment = mockComment();
        Book book = bookRepository.save(mockBook()).block();
        Long oldCount = commentRepository.count().block();

        client.post().uri("/v2/comment/{bookId}", book.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(comment), Comment.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Comment.class);

        assertFalse(commentRepository.count().block().equals(oldCount));
    }

    @Test
    public void testGetById() throws Exception {
        Comment comment = commentRepository.save(mockComment()).block();

        client.get().uri("/v2/comment/{id}", comment.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class);
    }

    @Test
    public void testUpdate() throws Exception {
        Book book = bookRepository.save(mockBook()).block();
        Comment comment = mockComment();
        comment.setBook(book);
        comment = commentRepository.save(comment).block();
        assertNotNull(comment.getId());
        comment.setText("newTestText");

        client.put().uri("/v2/comment/{id}/{bookId}", comment.getId(), book.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(comment), Comment.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class);

        comment = commentRepository.findByText("newTestText").block();
        assertEquals("newTestText", comment.getText());
    }

    @Test
    public void testDelete() throws Exception {
        Comment comment = commentRepository.save(mockComment()).block();
        assertNotNull(comment.getId());

        client.delete().uri("/v2/comment/{id}", comment.getId())
                .exchange()
                .expectStatus().isOk();

        assertNull(commentRepository.findByText(MOCK_TEXT).block());
    }

    @Override
    void clearMongo() {
        commentRepository.deleteAll().block();
        bookRepository.deleteAll().block();
    }

    @Override
    void webClientBuild() {
        client = WebTestClient
                .bindToController(new ReactiveCommentController(commentRepository, bookRepository))
                .build();
    }
}
