package com.github.pavelhe.service;

import java.util.*;

import com.github.pavelhe.config.*;
import com.github.pavelhe.model.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestMongoConfiguration.class})
public abstract class AbstractMongoDBIntegrationTestClass {

    final static String MOCK_NAME = "mockName";
    final static String MOCK_SURNAME = "mockSurname";
    final static String MOCK_DESCRIPTION = "mockDesc";
    final static String MOCK_TEXT = "mockCommentText";
    final static Long MOCK_ID = 1L;

    @After
    public void tearDown() {
        clearMongo();
    }

    public abstract void testCount() throws Exception;

    public abstract void testGetAll() throws Exception;

    public abstract void testGetById() throws Exception;

    public abstract void testGetByName() throws Exception;

    public abstract void testCreate() throws Exception;

    public abstract void testRemove() throws Exception;

    public abstract void testUpdate() throws Exception;

    abstract void clearMongo();

    Genre mockGenre() {
        return new Genre(MOCK_ID, MOCK_NAME);
    }

    Author mockAuthor() {
        return new Author(MOCK_ID, MOCK_NAME, MOCK_SURNAME);
    }

    Comment mockComment() {
        Comment comment = new Comment(MOCK_NAME, MOCK_TEXT);
        comment.setId(MOCK_ID);
        return comment;
    }

    Book mockBook() {
        Book book = new Book();
        book.setId(MOCK_ID);
        book.setName(MOCK_NAME);
        book.setGenre(mockGenre());
        book.setAuthor(mockAuthor());
        book.setDescription(MOCK_DESCRIPTION);
        Set<Comment> comments = new HashSet<>();
        comments.add(mockComment());
        book.setComments(comments);
        return book;
    }

}
