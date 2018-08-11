package com.github.pavelhe.repository.mongodb;

import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import static org.junit.Assert.*;


public class TestGenreMongoService extends AbstractMongoDBIntegrationTestClass {

    @Autowired
    @Qualifier("testMongoGenreService")
    private GenreService genreService;

    @Override
    @Test
    public void testCount() throws Exception {
        assertEquals(0, genreService.count().longValue());
        createMockGenre();
        assertEquals(1L, genreService.count().longValue());
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        assertEquals(0, genreService.getAll().size());
        createMockGenre();
        assertEquals(1, genreService.getAll().size());
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        createMockGenre();
        Genre genre = genreService.getById(MOCK_ID);
        assertNotNull(genre);
        assertEquals(MOCK_ID, genre.getId());
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        assertNull(genreService.getByName(MOCK_NAME));
        createMockGenre();
        Genre genre = genreService.getByName(MOCK_NAME);
        assertNotNull(genre);
        assertEquals(MOCK_ID, genre.getId());
        assertEquals(MOCK_NAME, genre.getName());
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Genre genre = createMockGenre();
        assertEquals(MOCK_ID, genre.getId());
        assertEquals(MOCK_NAME, genre.getName());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        Genre genre = createMockGenre();
        genreService.remove(MOCK_ID);
        genre = genreService.getByName(MOCK_NAME);
        assertNull(genre);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Genre genre = createMockGenre();
        genre.setName("test");
        genreService.update(genre);
        genre = genreService.getByName("test");
        assertEquals("test", genre.getName());
    }

    @Override
    void clearMongo() {
        genreService.remove(MOCK_ID);
    }

    private Genre createMockGenre() {
        Genre genre = mockGenre();
        genreService.create(genre);
        return genre;
    }
}
