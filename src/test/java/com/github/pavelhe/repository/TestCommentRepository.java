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
@EnableJpaRepositories(basePackageClasses = CommentRepository.class,
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef = "testJpaTransactionManager")
public class TestCommentRepository extends AbstractDaoIntegrationTestClass {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    @Test
    public void testCount() throws Exception {
        long oldSize = commentRepository.count();
        commentRepository.save(new Comment());
        long newSize = commentRepository.count();
        assertTrue(oldSize < newSize);
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        assertTrue(commentRepository.findAll().size() > 0);
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(1L);
        assertTrue(commentOptional.isPresent());
        Comment comment = commentOptional.get();
        assertNotNull(comment.getId());
        assertTrue(comment.getBooks().size() > 0);
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        Comment comment = commentRepository.findByName("Alfred").get(0);
        assertNotNull(comment);
        assertNotNull(comment.getId());
        assertTrue(comment.getBooks().size() > 0);
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Comment comment = new Comment("testName", "testText");
        assertNull(comment.getId());
        commentRepository.save(comment);
        comment = commentRepository.findByName("testName").get(0);
        assertNotNull(comment);
        assertNotNull(comment.getId());
        assertEquals("testName", comment.getName());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(1L);
        assertTrue(commentOptional.isPresent());
        commentRepository.delete(commentOptional.get());
        commentOptional = commentRepository.findById(1L);
        assertFalse(commentOptional.isPresent());
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Comment comment = commentRepository.findById(1L).orElseGet(null);
        assertNotNull(comment);
        assertEquals("Alfred", comment.getName());
        comment.setName("testName");
        commentRepository.save(comment);
        comment = commentRepository.findById(1L).orElseGet(null);
        assertNotNull(comment);
        assertEquals("testName", comment.getName());
    }
}
