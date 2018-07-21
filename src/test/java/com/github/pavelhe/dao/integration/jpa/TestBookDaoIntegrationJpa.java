package com.github.pavelhe.dao.integration.jpa;

import com.github.pavelhe.config.*;
import com.github.pavelhe.dao.*;
import com.github.pavelhe.dao.integration.*;
import com.github.pavelhe.model.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.transaction.annotation.*;


import static org.junit.Assert.*;


@ContextConfiguration(classes = {TestBasicConfiguration.class, TestJPAConfiguration.class})
@Transactional(value = "testJpaTransactionManager")
public class TestBookDaoIntegrationJpa extends AbstractDaoIntegrationTestClass {

    @Autowired
    @Qualifier("testBookDaoJpa")
    private BookDao bookDao;

    @Override
    @Test
    public void testCount() throws Exception {
        assertEquals(2, bookDao.count().longValue());
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        assertEquals(2, bookDao.getAll().size());
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        Book book = bookDao.getById(1L);
        assertNotNull(book);
        assertNotNull(book.getAuthor());
        assertNotNull(book.getGenre());
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        assertNotNull(bookDao.getByName("Neverwhere"));
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Book book = new Book("nameBook", "descBook");
        assertNull(book.getId());
        bookDao.create(book, 1L, 1L);
        book = bookDao.getById(3L);
        assertNotNull(book.getId());
        assertNotNull(book.getGenre());
        assertNotNull(book.getAuthor());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        assertNotNull(bookDao.getById(1L));
        bookDao.remove(1L);
        assertNull(bookDao.getById(1L));
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Book book = bookDao.getById(1L);
        assertNotEquals("test", book.getName());
        book.setName("test");
        bookDao.update(book);
        assertEquals("test", bookDao.getById(1L).getName());
    }
}
