package com.github.pavelhe.service;


import java.util.*;

import com.github.pavelhe.model.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import static org.junit.Assert.*;

public class TestCommentMongoService extends AbstractMongoDBIntegrationTestClass {

    @Autowired
    @Qualifier("testMongoCommentService")
    private CommentService commentService;

    @Override
    @Test
    public void testCount() throws Exception {
        assertEquals(0, commentService.count().longValue());
        createMockComment();
        assertEquals(1L, commentService.count().longValue());
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        assertEquals(0, commentService.getAll().size());
        createMockComment();
        assertEquals(1, commentService.getAll().size());
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        createMockComment();
        Comment comment = commentService.getById(MOCK_ID);
        assertNotNull(comment);
        assertEquals(MOCK_ID, comment.getId());
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        assertTrue(commentService.getByName(MOCK_NAME).size() == 0);
        createMockComment();
        List<Comment> comments = commentService.getByName(MOCK_NAME);
        assertNotNull(comments);
        assertEquals(1, comments.size());
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Comment comment = createMockComment();
        assertEquals(MOCK_ID, comment.getId());
        assertEquals(MOCK_NAME, comment.getName());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        Comment comment = createMockComment();
        commentService.remove(MOCK_ID);
        comment = commentService.getById(MOCK_ID);
        assertNull(comment);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Comment comment = createMockComment();
        comment.setName("test");
        commentService.update(comment);
        comment = commentService.getByName("test").get(0);
        assertEquals("test", comment.getName());
    }

    @Override
    void clearMongo() {
        commentService.remove(MOCK_ID);
    }

    private Comment createMockComment() {
        Comment comment = mockComment();
        commentService.create(comment, null);
        return comment;
    }
}
