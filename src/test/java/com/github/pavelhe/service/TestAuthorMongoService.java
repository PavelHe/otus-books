package com.github.pavelhe.service;


import com.github.pavelhe.model.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestAuthorMongoService extends AbstractMongoDBIntegrationTestClass {

    @Autowired
    @Qualifier("testMongoAuthorService")
    private AuthorService authorService;

    @Override
    @Test
    public void testCount() throws Exception {
        assertEquals(0, authorService.count().longValue());
        createMockAuthor();
        assertEquals(1L, authorService.count().longValue());
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        assertEquals(0, authorService.getAll().size());
        createMockAuthor();
        assertEquals(1, authorService.getAll().size());
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        createMockAuthor();
        Author author = authorService.getById(MOCK_ID);
        assertNotNull(author);
        assertEquals(MOCK_ID, author.getId());
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        assertNull(authorService.getByName(MOCK_NAME));
        createMockAuthor();
        Author author = authorService.getByName(MOCK_NAME);
        assertNotNull(author);
        assertEquals(MOCK_ID, author.getId());
        assertEquals(MOCK_NAME, author.getName());
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Author author = createMockAuthor();
        assertEquals(MOCK_ID, author.getId());
        assertEquals(MOCK_NAME, author.getName());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        Author author = createMockAuthor();
        authorService.remove(MOCK_ID);
        author = authorService.getByName(MOCK_NAME);
        assertNull(author);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Author author = createMockAuthor();
        author.setName("test");
        authorService.update(author);
        author = authorService.getByName("test");
        assertEquals("test", author.getName());
    }

    @Override
    void clearMongo() {
        authorService.remove(MOCK_ID);
    }

    private Author createMockAuthor() {
        Author author = mockAuthor();
        authorService.create(author);
        return author;
    }
}
