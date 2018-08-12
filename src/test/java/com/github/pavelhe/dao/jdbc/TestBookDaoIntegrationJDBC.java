package com.github.pavelhe.dao.jdbc;


import com.github.pavelhe.config.*;
import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.transaction.annotation.*;

import static org.junit.Assert.*;

@ContextConfiguration(classes = {TestBasicConfiguration.class, TestJDBCConfiguration.class})
@Transactional(value = "testJdbcTransactionManager")
public class TestBookDaoIntegrationJDBC extends AbstractDaoIntegrationTestClass {

    @Autowired
    @Qualifier("testBookJdbcDao")
    private BookDao bookDao;

    @Override
    @Test
    public void testCount() throws Exception {
        Long count = bookDao.count();
        assertEquals(2, count.longValue());
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
        assertNotNull(book.getId());
        assertEquals("Neverwhere", book.getName());
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        assertNotNull(bookDao.getByName("Neverwhere"));
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Book book = new Book("test name", "test description");
        bookDao.create(book, 1L, 1L);
        assertEquals(3, bookDao.count().longValue());
        book = bookDao.getById(3L);
        assertNotNull(book);
        assertNotNull(book.getId());
        assertEquals("test name", book.getName());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        assertEquals(2, bookDao.count().longValue());
        bookDao.remove(1L);
        assertEquals(1, bookDao.count().longValue());
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Book bookForUpdate = bookDao.getById(1L);
        assertEquals("Neverwhere", bookForUpdate.getName());
        bookForUpdate.setName("test name");
        bookDao.update(bookForUpdate);
        bookForUpdate = bookDao.getById(1L);
        assertEquals("test name", bookForUpdate.getName());
    }
}
