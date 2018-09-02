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

public class TestReactiveBookController extends AbstractReactiveControllerTestClass {

    @Autowired
    private ReactiveBookRepository repository;

    @Test
    public void testGetAll() throws Exception {
        Book book = repository.save(mockBook()).block();
        assertNotNull(book.getId());

        client.get().uri("/v2/book")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Genre.class);
    }

    @Test
    public void testSave() {
        Book book = mockBook();
        Long oldCount = repository.count().block();

        client.post().uri("/v2/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(book), Book.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class);

        assertFalse(repository.count().block().equals(oldCount));
    }

    @Test
    public void testGetById() throws Exception {
        Book book = repository.save(mockBook()).block();

        client.get().uri("/v2/book/{id}", book.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class);
    }

    @Test
    public void testUpdate() throws Exception {
        Book book = repository.save(mockBook()).block();
        assertNotNull(book.getId());
        book.setName("newTestName");

        client.put().uri("/v2/book/{id}", book.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(book), Book.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class);

        book = repository.findByName("newTestName").block();
        assertEquals("newTestName", book.getName());
    }

    @Test
    public void testDelete() throws Exception {
        Book book = repository.save(mockBook()).block();
        assertNotNull(book.getId());

        client.delete().uri("/v2/book/{id}", book.getId())
                .exchange()
                .expectStatus().isOk();

        assertNull(repository.findByName(MOCK_NAME).block());
    }

    @Override
    void clearMongo() {
        repository.deleteAll().block();
    }

    @Override
    void webClientBuild() {
        client = WebTestClient
                .bindToController(new ReactiveBookController(repository))
                .build();
    }
}
