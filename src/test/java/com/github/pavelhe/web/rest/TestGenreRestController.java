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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestGenreRestController extends AbstractControllerTestClass {

    private static final String GENRE_URL = "/rest/genre";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GenreRepository genreRepository;

    @Override
    protected MockMvc buildMockMvc() {
        return MockMvcBuilders
                .standaloneSetup(new GenreRestController(genreRepository))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(GENRE_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        String url = GENRE_URL + "/" + 1L;
        mockMvc.perform(get(url))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByName() throws Exception {
        String url = GENRE_URL + "/name/Fantasy";
        mockMvc.perform(get(url))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        Genre createdGenre = new Genre("test");
        String jsonGenre = objectMapper.writeValueAsString(createdGenre);
        mockMvc.perform(post(GENRE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonGenre))
                .andExpect(status().isCreated());

        assertTrue(genreRepository.findByName(createdGenre.getName()).isPresent());
    }

    @Test
    public void testUpdate() throws Exception {
        Optional<Genre> optional = genreRepository.findByName("Fantasy");
        assertTrue(optional.isPresent());

        Genre updatedGenre = optional.get();
        Long id = updatedGenre.getId();
        updatedGenre.setName("test");
        String genreJson = objectMapper.writeValueAsString(updatedGenre);
        String url = GENRE_URL + "/" + id;
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(genreJson))
                .andExpect(status().isOk());

        optional = genreRepository.findByName("test");
        assertTrue(optional.isPresent());
        updatedGenre = optional.get();
        assertEquals(id, updatedGenre.getId());
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;
        String url = GENRE_URL + "/" + id;
        Optional<Genre> optional = genreRepository.findById(id);
        assertTrue(optional.isPresent());

        mockMvc.perform(delete(url))
                .andExpect(status().isOk());

        optional = genreRepository.findById(id);
        assertFalse(optional.isPresent());
    }
}
