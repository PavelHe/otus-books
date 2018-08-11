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
@EnableJpaRepositories(basePackageClasses = BookRepository.class,
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef = "testJpaTransactionManager")
public class TestBookRepository extends AbstractDaoIntegrationTestClass {

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Test
    public void testCount() throws Exception {
        long oldSize = bookRepository.count();
        bookRepository.save(new Book());
        long newSize = bookRepository.count();
        assertTrue(oldSize < newSize);
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        assertTrue(bookRepository.findAll().size() > 0);
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        Optional<Book> bookOptional = bookRepository.findById(1L);
        assertTrue(bookOptional.isPresent());
        assertTrue(bookOptional.get().getComments().size() > 0);
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        Optional<Book> bookOptional = bookRepository.findByName("Neverwhere");
        assertTrue(bookOptional.isPresent());
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        bookRepository.save(new Book("testBook", "testDesc"));
        Optional<Book> bookOptional = bookRepository.findByName("testBook");
        assertTrue(bookOptional.isPresent());
        Book book = bookOptional.get();
        assertNotNull(book.getId());
        assertEquals("testBook", book.getName());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        Optional<Book> bookOptional = bookRepository.findById(1L);
        assertTrue(bookOptional.isPresent());
        bookRepository.delete(bookOptional.get());
        bookOptional = bookRepository.findById(1L);
        assertFalse(bookOptional.isPresent());
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Book book = bookRepository.findByName("Neverwhere").orElseGet(null);
        assertNotNull(book);
        assertEquals("Neverwhere", book.getName());
        book.setName("testName");
        book = bookRepository.findByName("testName").orElseGet(null);
        assertNotNull(book);
        assertEquals("testName", book.getName());
    }
}
