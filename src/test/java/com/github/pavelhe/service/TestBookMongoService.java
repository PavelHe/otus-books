package com.github.pavelhe.service;


import com.github.pavelhe.model.mongodb.models.*;
import com.github.pavelhe.service.mongodb.interfaces.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestBookMongoService extends AbstractMongoDBIntegrationTestClass {

    @Autowired
    @Qualifier("testMongoBookService")
    private com.github.pavelhe.service.mongodb.interfaces.BookService bookService;

    @Override
    @Test
    public void testCount() throws Exception {
        assertEquals(0, bookService.count().longValue());
        createMockBook();
        assertEquals(1L, bookService.count().longValue());
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        assertEquals(0, bookService.getAll().size());
        createMockBook();
        assertEquals(1, bookService.getAll().size());
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        createMockBook();
        Book book = bookService.getById(MOCK_ID);
        assertNotNull(book);
        assertEquals(MOCK_ID, book.getId());
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        assertNull(bookService.getByName(MOCK_NAME));
        createMockBook();
        Book book = bookService.getByName(MOCK_NAME);
        assertNotNull(book);
        assertEquals(MOCK_ID, book.getId());
        assertEquals(MOCK_NAME, book.getName());
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Book book = createMockBook();
        book = bookService.getByName(MOCK_NAME);
        assertEquals(MOCK_ID, book.getId());
        assertEquals(MOCK_NAME, book.getName());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        Book book = createMockBook();
        bookService.remove(MOCK_ID);
        book = bookService.getByName(MOCK_NAME);
        assertNull(book);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Book book = createMockBook();
        book.setName("test");
        bookService.update(book);
        book = bookService.getByName("test");
        assertEquals("test", book.getName());
    }

    @Override
    void clearMongo() {
        bookService.remove(MOCK_ID);
    }

    private Book createMockBook() {
        Book book = mockBook();
        bookService.create(book);
        return book;
    }
}
