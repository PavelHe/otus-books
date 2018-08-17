package com.github.pavelhe.web;


import java.util.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.multipart.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestBookController extends AbstractControllerTestClass {

    @Autowired
    @Qualifier("testGenreDaoJpa")
    private GenreDao genreDao;
    @Autowired
    @Qualifier("testBookDaoJpa")
    private BookDao bookDao;
    @Autowired
    @Qualifier("testAuthorDaoJpa")
    private AuthorDao authorDao;

    @MockBean
    private MultipartFile multipartFile;

    @Override
    protected MockMvc buildMockMvc() {
        AuthorService authorService = new AuthorServiceImpl(authorDao);
        GenreService genreService = new GenreServiceImpl(genreDao);
        BookService bookService = new BookServiceImpl(bookDao);
        return MockMvcBuilders
                .standaloneSetup(new BookController(bookService, authorService, genreService))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testGetAllBook() throws Exception {
        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andExpect(model().attributeExists("bookList", "authorList", "genreList", "book"))
                .andExpect(model().attribute("bookList", hasItem(
                        allOf(
                                hasProperty("id"),
                                hasProperty("name"),
                                hasProperty("author"),
                                hasProperty("genre"),
                                hasProperty("comments"),
                                hasProperty("description")
                        )
                )))
                .andExpect(model().attribute("book", notNullValue()));
    }

    @Test
    public void testGetBookInfo() throws Exception {
        mockMvc.perform(get("/book/info/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookInfo"))
                .andExpect(model().attributeExists("book", "authorList", "genreList", "comment"))
                .andExpect(model().attribute("comment", notNullValue()));
    }

    @Test
    public void testRemoveBookById() throws Exception {
        Book book = new Book("test", new Author(1L, "test", "test"),
                new Genre(1L, "test"), "test");
        bookDao.create(book);
        Long id = bookDao.getByName(book.getName()).getId();
        Long countBefore = bookDao.count();
        assertNotNull(id);

        mockMvc.perform(get("/book/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));

        Long countAfter = genreDao.count();
        assertTrue(countBefore > countAfter);
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = bookDao.getByName("Neverwhere");
        String mockName = "mockName";
        book.setName(mockName);
        Long bookCount = genreDao.count();

        mockMvc.perform(multipart("/book/add")
                .content("multipart/*")
                .flashAttr("book", book)
                .flashAttr("photo", multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));

        book = bookDao.getByName(mockName);
        assertTrue(Objects.nonNull(book));
        assertTrue(Objects.equals(bookCount, bookDao.count()));
    }

    @Test
    public void testAddBook() throws Exception {
        Book book = new Book("test", new Author(1L, "test", "test"),
                new Genre(1L, "test"), "test");
        Long bookCount = genreDao.count();

        mockMvc.perform(multipart("/book/add")
                .content("multipart/*")
                .flashAttr("book", book)
                .flashAttr("photo", multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));

        assertTrue(Objects.nonNull(book));
        assertFalse(Objects.equals(bookCount, bookDao.count()));
    }
}
