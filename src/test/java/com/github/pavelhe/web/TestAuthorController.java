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

public class TestAuthorController extends AbstractControllerTestClass {

    @Autowired
    @Qualifier("testAuthorDaoJpa")
    private AuthorDao authorDao;

    @Override
    protected MockMvc buildMockMvc() {
        return MockMvcBuilders
                .standaloneSetup(new AuthorController(new AuthorServiceImpl(authorDao)))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testGetAllAuthors() throws Exception {
        mockMvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(view().name("authors"))
                .andExpect(model().attributeExists("authorList", "author"))
                .andExpect(model().attribute("authorList", hasItem(
                        allOf(
                                hasProperty("id"),
                                hasProperty("name"),
                                hasProperty("surname")
                        )
                )))
                .andExpect(model().attribute("author", notNullValue()));
    }

    @Test
    public void testRemoveAuthorById() throws Exception {
        Author author = new Author("test", "test");
        authorDao.create(author);
        Long id = authorDao.getByName(author.getName()).getId();
        Long countBefore = authorDao.count();
        assertNotNull(id);

        mockMvc.perform(get("/author/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));

        Long countAfter = authorDao.count();
        assertTrue(countBefore > countAfter);
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        Author author = authorDao.getByName("Neil");
        String mockName = "mockName";
        author.setName(mockName);
        Long authorCount = authorDao.count();

        mockMvc.perform(post("/author/add")
                .flashAttr("author", author))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));

        author = authorDao.getByName(mockName);
        assertTrue(Objects.nonNull(author));
        assertTrue(Objects.equals(authorCount, authorDao.count()));
    }

    @Test
    public void testAddAuthor() throws Exception {
        Author author = new Author("test", "test");
        Long authorCount = authorDao.count();

        mockMvc.perform(post("/author/add")
                .flashAttr("author", author))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));

        author = authorDao.getByName("test");
        assertNotNull(author);
        assertFalse(Objects.equals(authorCount, authorDao.count()));
    }

}
