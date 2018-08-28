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

public class TestAuthorRestController extends AbstractControllerTestClass {

    private static final String AUTHOR_URL = "/rest/author";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    protected MockMvc buildMockMvc() {
        return MockMvcBuilders
                .standaloneSetup(new AuthorRestController(authorRepository))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(AUTHOR_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        String url = AUTHOR_URL + "/" + 1L;
        mockMvc.perform(get(url))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByFullName() throws Exception {
        String url = AUTHOR_URL + "/fullname/Neil/Gaiman";
        mockMvc.perform(get(url))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        Author createdAuthor = new Author("test", "test");
        String jsonAuthor = objectMapper.writeValueAsString(createdAuthor);
        mockMvc.perform(post(AUTHOR_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonAuthor))
                .andExpect(status().isCreated());

        assertTrue(authorRepository.findByName(createdAuthor.getName()).isPresent());
    }

    @Test
    public void testUpdate() throws Exception {
        Optional<Author> optional = authorRepository.findByName("Neil");
        assertTrue(optional.isPresent());

        Author updatedAuthor = optional.get();
        Long id = updatedAuthor.getId();
        updatedAuthor.setName("test");
        String jsonAuthor = objectMapper.writeValueAsString(updatedAuthor);
        String url = AUTHOR_URL + "/" + id;
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonAuthor))
                .andExpect(status().isOk());

        optional = authorRepository.findByName("test");
        assertTrue(optional.isPresent());
        updatedAuthor = optional.get();
        assertEquals(id, updatedAuthor.getId());
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;
        String url = AUTHOR_URL + "/" + id;
        Optional<Author> optional = authorRepository.findById(id);
        assertTrue(optional.isPresent());

        mockMvc.perform(delete(url))
                .andExpect(status().isOk());

        optional = authorRepository.findById(id);
        assertFalse(optional.isPresent());
    }
}
