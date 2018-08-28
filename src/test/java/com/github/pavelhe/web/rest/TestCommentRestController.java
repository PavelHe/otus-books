package com.github.pavelhe.web.rest;


import java.util.*;

import com.fasterxml.jackson.databind.*;
import com.github.pavelhe.model.*;
import com.github.pavelhe.repository.*;
import com.github.pavelhe.web.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestCommentRestController extends AbstractControllerTestClass {

    private static final String COMMENT_URL = "/rest/comment";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    protected MockMvc buildMockMvc() {
        return MockMvcBuilders
                .standaloneSetup(new CommentRestController(commentRepository, bookRepository))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(COMMENT_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        String url = COMMENT_URL + "/" + 1L;
        mockMvc.perform(get(url))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        String bookId = "1";
        String url = COMMENT_URL + "/" + bookId;
        Comment createdComment = new Comment("test", "test");
        String jsonComment = objectMapper.writeValueAsString(createdComment);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonComment))
                .andExpect(status().isCreated());

        assertTrue(commentRepository.findByName(createdComment.getName()).size() > 0);
    }

    @Test
    public void testUpdate() throws Exception {
        Optional<Comment> optional = commentRepository.findById(1L);
        assertTrue(optional.isPresent());

        Comment updatedComment = optional.get();
        Long id = updatedComment.getId();
        String bookId = "1";
        updatedComment.setText("test");
        String jsonComment = objectMapper.writeValueAsString(updatedComment);
        String url = COMMENT_URL + "/" + id + "/" + bookId;
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonComment))
                .andExpect(status().isOk());

        optional = commentRepository.findById(id);
        assertTrue(optional.isPresent());
        updatedComment = optional.get();
        assertEquals("test", updatedComment.getText());
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;
        String url = COMMENT_URL + "/" + id;
        Optional<Comment> optional = commentRepository.findById(id);
        assertTrue(optional.isPresent());

        mockMvc.perform(delete(url))
                .andExpect(status().isOk());

        optional = commentRepository.findById(id);
        assertFalse(optional.isPresent());
    }

}
