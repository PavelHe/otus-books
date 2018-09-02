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


public class TestReactiveGenreController extends AbstractReactiveControllerTestClass {

    @Autowired
    private ReactiveGenreRepository repository;

    @Test
    public void testGetAll() throws Exception {
        Genre genre = repository.save(mockGenre()).block();
        assertNotNull(genre.getId());

        client.get().uri("/v2/genre")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Genre.class);
    }

    @Test
    public void testSave() {
        Genre genre = mockGenre();
        Long oldCount = repository.count().block();

        client.post().uri("/v2/genre")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(genre), Genre.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class);

        assertFalse(repository.count().block().equals(oldCount));
    }

    @Test
    public void testGetById() throws Exception {
        Genre genre = repository.save(mockGenre()).block();

        client.get().uri("/v2/genre/{id}", genre.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class);
    }

    @Test
    public void testUpdate() throws Exception {
        Genre genre = repository.save(mockGenre()).block();
        assertNotNull(genre.getId());
        genre.setName("newTestName");

        client.put().uri("/v2/genre/{id}", genre.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(genre), Genre.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class);

        genre = repository.findByName("newTestName").block();
        assertEquals("newTestName", genre.getName());
    }

    @Test
    public void testDelete() throws Exception {
        Genre genre = repository.save(mockGenre()).block();
        assertNotNull(genre.getId());

        client.delete().uri("/v2/genre/{id}", genre.getId())
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
                .bindToController(new ReactiveGenreController(repository))
                .build();
    }
}
