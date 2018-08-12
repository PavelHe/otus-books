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
public class TestGenreDaoIntegrationJDBC extends AbstractDaoIntegrationTestClass {

    @Autowired
    @Qualifier("testGenreJdbcDao")
    private GenreDao genreDao;

    @Override
    @Test
    public void testCount() {
        Long count = genreDao.count();
        assertEquals(2, count.longValue());
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        assertEquals(2, genreDao.getAll().size());
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        Genre fantasy = genreDao.getById(1L);
        assertEquals("Fantasy", fantasy.getName());
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        assertNotNull(genreDao.getByName("Fantasy"));
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Genre newGenre = new Genre("test");
        genreDao.create(newGenre);
        newGenre = genreDao.getById(3L);
        assertEquals("test", newGenre.getName());
        assertEquals(3, newGenre.getId().longValue());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        genreDao.remove(1L);
        assertEquals(1, genreDao.count().longValue());
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Genre genreForUpdate = genreDao.getById(1L);
        assertEquals("Fantasy", genreForUpdate.getName());
        genreForUpdate.setName("test");
        genreDao.update(genreForUpdate);
        assertEquals("test", genreDao.getById(1L).getName());
    }

}
