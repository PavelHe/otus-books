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
public class TestGenreDaoIntegrationJPA extends AbstractDaoIntegrationTestClass {

    @Autowired
    @Qualifier("testGenreDaoJpa")
    private GenreDao genreDao;

    @Override
    @Test
    public void testCount() throws Exception {
        Long oldSize = genreDao.count();
        genreDao.create(new Genre());
        Long newSize = genreDao.count();
        assertTrue(oldSize < newSize);
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        assertEquals(2, genreDao.getAll().size());
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        assertNotNull(genreDao.getById(1L));
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        Genre genre = genreDao.getByName("Fantasy");
        assertNotNull(genre);
        assertTrue(genre.getBooks().size() > 0);
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Genre genre = new Genre("test");
        genreDao.create(genre);
        assertNotNull(genreDao.getByName("test"));
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        assertNotNull(genreDao.getById(1L));
        genreDao.remove(1L);
        assertNull(genreDao.getById(1L));
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Genre genre = genreDao.getById(1L);
        genre.setName("test");
        genreDao.update(genre);
        assertNotNull(genreDao.getByName("test"));
    }
}
