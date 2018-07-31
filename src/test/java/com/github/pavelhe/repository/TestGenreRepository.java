package com.github.pavelhe.repository;

import java.util.*;

import com.github.pavelhe.config.*;
import com.github.pavelhe.dao.integration.*;
import com.github.pavelhe.model.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.test.context.*;
import org.springframework.transaction.annotation.*;

import static org.junit.Assert.*;

@ContextConfiguration(classes = {TestBasicConfiguration.class, TestJPAConfiguration.class,})
@Transactional(value = "testJpaTransactionManager")
@EnableJpaRepositories(basePackageClasses = GenreRepository.class,
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef = "testJpaTransactionManager")
public class TestGenreRepository extends AbstractDaoIntegrationTestClass {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    @Test
    public void testCount() throws Exception {
        long oldSize = genreRepository.count();
        genreRepository.save(new Genre());
        long newSize = genreRepository.count();
        assertTrue(oldSize < newSize);
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        long byAllSizeOf = genreRepository.findAll().size();
        boolean isValidListOfGenres = byAllSizeOf != 0 && byAllSizeOf > 1;
        assertTrue(isValidListOfGenres);
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        Optional<Genre> genreOptional = genreRepository.findById(1L);
        assertTrue(genreOptional.isPresent());
        Genre genre = genreOptional.get();
        assertTrue(genre.getBooks().size() > 0);
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        Optional<Genre> genreOptional = genreRepository.findByName("Fantasy");
        assertTrue(genreOptional.isPresent());
        Genre genre = genreOptional.get();
        assertEquals("Fantasy", genre.getName());
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Genre genre = new Genre("testGenre");
        assertNull(genre.getId());
        genreRepository.save(genre);
        Optional<Genre> genreOptional = genreRepository.findByName("testGenre");
        assertTrue(genreOptional.isPresent());
        genre = genreOptional.get();
        assertNotNull(genre.getId());
        assertEquals("testGenre", genre.getName());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        Optional<Genre> genreOptional = genreRepository.findByName("Fantasy");
        assertTrue(genreOptional.isPresent());
        genreRepository.delete(genreOptional.get());
        genreOptional = genreRepository.findByName("Fantasy");
        assertFalse(genreOptional.isPresent());
    }

    @Test
    public void testRemoveByIdSuccessfully() throws Exception {
        boolean wasRemoved = genreRepository.delete(1L) == 1;
        assertTrue(wasRemoved);
    }

    @Test
    public void testRemoveByIdUnsuccessfully() throws Exception {
        boolean notRemoved = genreRepository.delete(1000000L) == 0;
        assertTrue(notRemoved);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Genre genre = genreRepository.findByName("Fantasy").orElseGet(null);
        assertNotNull(genre);
        assertEquals("Fantasy", genre.getName());
        genre.setName("testGenre");
        genreRepository.save(genre);
        genre = genreRepository.findByName("testGenre").orElseGet(null);
        assertNotNull(genre);
        assertEquals("testGenre", genre.getName());
    }
}
