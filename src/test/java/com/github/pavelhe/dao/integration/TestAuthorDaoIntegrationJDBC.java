package com.github.pavelhe.dao.integration;

import java.util.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import static org.junit.Assert.*;


public class TestAuthorDaoIntegrationJDBC extends AbstractDaoIntegrationTestClass {

    @Autowired
    @Qualifier("testAuthorDao")
    private AuthorDao authorDao;

    @Override
    @Test
    public void testCount() throws Exception {
        Long count = authorDao.count();
        assertEquals(2, count.longValue());
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        List<Author> authors = authorDao.getAll();
        assertNotNull(authors);
        assertEquals(2, authors.size());
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        Author author = authorDao.getById(1L);
        assertNotNull(author);
        assertNotNull(author.getId());
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Author author = new Author("test name", "test surname");
        authorDao.create(author);
        assertEquals(3, authorDao.getAll().size());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        assertEquals(2, authorDao.getAll().size());
        authorDao.remove(1L);
        assertEquals(1, authorDao.getAll().size());
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Author author = authorDao.getById(1L);
        assertEquals("Neil", author.getName());
        author.setName("Test");
        authorDao.update(author);
        assertEquals("Test", authorDao.getById(1L).getName());
    }

}
