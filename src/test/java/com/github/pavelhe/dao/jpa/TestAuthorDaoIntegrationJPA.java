package com.github.pavelhe.dao.jpa;

import com.github.pavelhe.config.*;
import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.transaction.annotation.*;


import static org.junit.Assert.*;


@ContextConfiguration(classes = {TestBasicConfiguration.class, TestJPAConfiguration.class})
@Transactional(value = "testJpaTransactionManager")
public class TestAuthorDaoIntegrationJPA extends AbstractDaoIntegrationTestClass {

    @Autowired
    @Qualifier("testAuthorDaoJpa")
    private AuthorDao authorDao;

    @Override
    @Test
    public void testCount() throws Exception {
        Long oldSize = authorDao.count();
        authorDao.create(new Author("test", "test"));
        Long newSize = authorDao.count();
        assertTrue(oldSize < newSize);
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        assertEquals(2, authorDao.getAll().size());
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        Author author = authorDao.getById(1L);
        assertNotNull(author);
        assertTrue(author.getBooks().size() > 0);
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        assertNotNull(authorDao.getByName("Neil"));
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Author author = new Author("test", "tesS");
        authorDao.create(author);
        author = authorDao.getByName("test");
        assertNotNull(author);
        assertNotNull(author.getId());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        assertNotNull(authorDao.getById(1L));
        authorDao.remove(1L);
        assertNull(authorDao.getById(1L));
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Author author = authorDao.getById(1L);
        assertNotEquals("test", author.getName());
        author.setName("test");
        authorDao.update(author);
        assertNotNull(authorDao.getByName("test"));
    }
}
