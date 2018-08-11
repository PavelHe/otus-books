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
@EnableJpaRepositories(basePackageClasses = AuthorRepository.class,
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef = "testJpaTransactionManager")
public class TestAuthorRepository extends AbstractDaoIntegrationTestClass {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    @Test
    public void testCount() throws Exception {
        long oldSize = authorRepository.count();
        authorRepository.save(new Author());
        long newSize = authorRepository.count();
        assertTrue(oldSize < newSize);
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        long byAllSizeOf = authorRepository.findAll().size();
        boolean isValidListOfAuthors = byAllSizeOf != 0 && byAllSizeOf > 1;
        assertTrue(isValidListOfAuthors);
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        Optional<Author> authorOptional = authorRepository.findById(1L);
        assertTrue(authorOptional.isPresent());
        Author author = authorOptional.get();
        assertTrue(author.getBooks().size() > 0);
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        Optional<Author> authorOptional = authorRepository.findByName("Neil");
        assertTrue(authorOptional.isPresent());
        Author author = authorOptional.get();
        assertTrue(author.getBooks().size() > 0);
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Author author = new Author("testName", "testSurname");
        assertNull(author.getId());
        authorRepository.save(author);
        Optional<Author> authorOptional = authorRepository.findByName("testName");
        assertTrue(authorOptional.isPresent());
        author = authorOptional.get();
        assertNotNull(author.getId());
        assertEquals("testName", author.getName());
        assertEquals("testSurname", author.getSurname());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        Optional<Author> authorOptional = authorRepository.findByName("Neil");
        assertTrue(authorOptional.isPresent());
        authorRepository.delete(authorOptional.get());
        authorOptional = authorRepository.findByName("Neil");
        assertFalse(authorOptional.isPresent());
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Author author = authorRepository.findByName("Neil").orElseGet(null);
        assertNotNull(author);
        assertEquals("Neil", author.getName());
        author.setName("testName");
        authorRepository.save(author);
        author = authorRepository.findByName("testName").orElseGet(null);
        assertNotNull(author);
        assertEquals("testName", author.getName());
    }
}
