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
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class TestGenreController extends AbstractControllerTestClass {

    @Autowired
    @Qualifier("testGenreDaoJpa")
    private GenreDao genreDao;

    @Override
    protected MockMvc buildMockMvc() {
        return MockMvcBuilders
                .standaloneSetup(new GenreController(new GenreServiceImpl(genreDao)))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testGetAllGenre() throws Exception {
        mockMvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(view().name("genres"))
                .andExpect(model().attributeExists("genreList", "genre"))
                .andExpect(model().attribute("genreList", hasItem(
                        allOf(
                                hasProperty("id"),
                                hasProperty("name")
                        )
                )))
                .andExpect(model().attribute("genre", notNullValue()));
    }

    @Test
    public void testRemoveGenreById() throws Exception {
        Genre genre = new Genre("test");
        genreDao.create(genre);
        Long id = genreDao.getByName(genre.getName()).getId();
        Long countBefore = genreDao.count();
        assertNotNull(id);

        mockMvc.perform(get("/genre/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/genre"));

        Long countAfter = genreDao.count();
        assertTrue(countBefore > countAfter);
    }

    @Test
    public void testUpdateGenre() throws Exception {
        Genre genre = genreDao.getByName("Fantasy");
        String mockName = "mockName";
        genre.setName(mockName);
        Long authorCount = genreDao.count();

        mockMvc.perform(post("/genre/add")
                .flashAttr("genre", genre))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/genre"));

        genre = genreDao.getByName(mockName);
        assertTrue(Objects.nonNull(genre));
        assertTrue(Objects.equals(authorCount, genreDao.count()));
    }

    @Test
    public void testAddGenre() throws Exception {
        Genre genre = new Genre("test");
        Long genreCount = genreDao.count();

        mockMvc.perform(post("/genre/add")
                .flashAttr("genre", genre))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/genre"));

        genre = genreDao.getByName("test");
        assertNotNull(genre);
        assertFalse(Objects.equals(genreCount, genreDao.count()));
    }
}
