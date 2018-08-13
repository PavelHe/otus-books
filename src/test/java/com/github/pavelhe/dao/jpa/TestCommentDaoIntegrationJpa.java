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
public class TestCommentDaoIntegrationJpa extends AbstractDaoIntegrationTestClass {

    @Autowired
    @Qualifier("testCommentDaoJpa")
    private CommentDao commentDao;

    @Override
    @Test
    public void testCount() throws Exception {
        Long oldSize = commentDao.count();
        commentDao.create(new Comment("test", "test"));
        Long newSize = commentDao.count();
        assertTrue(oldSize < newSize);
    }

    @Override
    @Test
    public void testGetAll() throws Exception {
        assertEquals(1, commentDao.getAll().size());
    }

    @Override
    @Test
    public void testGetById() throws Exception {
        Comment comment = commentDao.getById(1L);
        assertNotNull(comment);
        assertNotNull(comment.getTimeOfCommit());
    }

    @Override
    @Test
    public void testGetByName() throws Exception {
        commentDao.create(new Comment("name", "text"));
        assertNotNull(commentDao.getByName("name"));
    }

    @Override
    @Test
    public void testCreate() throws Exception {
        Comment comment = new Comment("create", "create");
        comment.setBook(new Book(1L,"test", new Author(1L, "name", "surname"),
                new Genre(1L, "test"), "desc"));
        assertNull(comment.getId());
        commentDao.create(comment);
        comment = commentDao.getByName("create").get(0);
        assertNotNull(comment.getId());
        Book book = comment.getBook();
        assertNotNull(book);
        assertEquals("test", book.getName());
    }

    @Override
    @Test
    public void testRemove() throws Exception {
        assertTrue(commentDao.count() > 0);
        commentDao.remove(1L);
        assertTrue(commentDao.count() == 0);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
        Comment comment = commentDao.getById(1L);
        assertFalse(comment.getText().equals("newText"));
        assertFalse(comment.getName().equals("newName"));
        comment.setText("newText");
        comment.setName("newName");
        commentDao.update(comment);
        comment = commentDao.getById(1L);
        assertTrue(comment.getText().equals("newText"));
        assertTrue(comment.getName().equals("newName"));
    }

}
