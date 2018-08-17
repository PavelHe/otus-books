package com.github.pavelhe.web;


import java.util.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class TestCommentController extends AbstractControllerTestClass {

    @Autowired
    @Qualifier("testCommentDaoJpa")
    private CommentDao commentDao;
    @Autowired
    @Qualifier("testBookDaoJpa")
    private BookDao bookDao;

    @Override
    protected MockMvc buildMockMvc() {
        return MockMvcBuilders
                .standaloneSetup(new CommentController(new CommentServiceImpl(commentDao),
                        new BookServiceImpl(bookDao)))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testRemoveCommentById() throws Exception {
        Comment comment = new Comment("test", "test");
        comment.setBook(bookDao.getById(1L));
        commentDao.create(comment);
        Long id = commentDao.getByName(comment.getName()).get(0).getId();
        Long countBefore = commentDao.count();
        assertNotNull(id);

        mockMvc.perform(get("/comment/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book/info/" + comment.getBook().getId()));

        Long countAfter = commentDao.count();
        assertTrue(countBefore > countAfter);
    }

    @Test
    public void testAddComment() throws Exception {
        Comment comment = new Comment("test", "test");
        Long genreCount = commentDao.count();

        mockMvc.perform(post("/comment/add")
                .flashAttr("comment", comment)
                .param("bookId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book/info/1"));

        comment = commentDao.getByName("test").get(0);
        assertNotNull(comment);
        assertFalse(Objects.equals(genreCount, commentDao.count()));
    }
}
