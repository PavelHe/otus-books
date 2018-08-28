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

public class TestBookRestController extends AbstractControllerTestClass {

    private static final String BOOK_URL = "/rest/book";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository bookRepository;

    @Override
    protected MockMvc buildMockMvc() {
        return MockMvcBuilders
                .standaloneSetup(new BookRestController(bookRepository))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(BOOK_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        String url = BOOK_URL + "/" + 1L;
        mockMvc.perform(get(url))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        Author author = new Author(1L,"test", "test");
        Genre genre = new Genre(1L, "test");
        Book createdBook = new Book("testBook", author, genre, "testDesc");
        String jsonAuthor = objectMapper.writeValueAsString(createdBook);
        mockMvc.perform(post(BOOK_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonAuthor))
                .andExpect(status().isCreated());

        assertTrue(bookRepository.findByName(createdBook.getName()).isPresent());
    }

    @Test
    public void testUpdate() throws Exception {
        Optional<Book> optional = bookRepository.findByName("Neverwhere");
        assertTrue(optional.isPresent());

        Book updatedBook = optional.get();
        Long id = updatedBook.getId();
        updatedBook.setName("testBook");
        String jsonBook = objectMapper.writeValueAsString(updatedBook);
        String url = BOOK_URL + "/" + id;
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonBook))
                .andExpect(status().isOk());

        optional = bookRepository.findByName("testBook");
        assertTrue(optional.isPresent());
        updatedBook = optional.get();
        assertEquals(id, updatedBook.getId());
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;
        String url = BOOK_URL + "/" + id;
        Optional<Book> optional = bookRepository.findById(id);
        assertTrue(optional.isPresent());

        mockMvc.perform(delete(url))
                .andExpect(status().isOk());

        optional = bookRepository.findById(id);
        assertFalse(optional.isPresent());
    }
}
