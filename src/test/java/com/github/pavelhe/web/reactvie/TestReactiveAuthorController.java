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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestReactiveAuthorController extends AbstractReactiveControllerTestClass {

    @Autowired
    private ReactiveAuthorRepository repository;

    @Test
    public void testGetAll() throws Exception {
        Author author = repository.save(mockAuthor()).block();
        assertNotNull(author.getId());

        client.get().uri("/v2/author")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Author.class);
    }

    @Test
    public void testSave() {
        Author author = mockAuthor();
        Long oldCount = repository.count().block();

        client.post().uri("/v2/author")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(author), Author.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class);

        assertFalse(repository.count().block().equals(oldCount));
    }

    @Test
    public void testGetById() throws Exception {
        Author author = repository.save(mockAuthor()).block();

        client.get().uri("/v2/author/{id}", author.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class);
    }

    @Test
    public void testGetByFullName() throws Exception {
        Author author = repository.save(mockAuthor()).block();

        client.get().uri("/v2/author/fullname/{name}/{surname}", author.getName(), author.getSurname())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Author.class);
    }

    @Test
    public void testUpdate() throws Exception {
        Author author = repository.save(mockAuthor()).block();
        assertNotNull(author.getId());
        author.setName("newTestName");

        client.put().uri("/v2/author/{id}", author.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(author), Author.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class);

        author = repository.findByName("newTestName").block();
        assertEquals("newTestName", author.getName());
    }

    @Test
    public void testDelete() throws Exception {
        Author author = repository.save(mockAuthor()).block();
        assertNotNull(author.getId());

        client.delete().uri("/v2/author/{id}", author.getId())
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
                .bindToController(new ReactiveAuthorController(repository))
                .build();
    }
}
