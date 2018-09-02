package com.github.pavelhe.web.reactvie;

import com.github.pavelhe.config.*;
import com.github.pavelhe.model.mongodb.models.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.autoconfigure.web.reactive.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.web.reactive.server.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestReactiveConfiguration.class})
@WebFluxTest
@AutoConfigureWebTestClient
public abstract class AbstractReactiveControllerTestClass {

    protected static final String MOCK_ID = "1";
    protected static final String MOCK_NAME = "test";
    protected static final String MOCK_SURNAME = "test";
    protected static final String MOCK_TEXT = "test text";
    protected static final String MOCK_DESCRIPTION = "description test";

    protected WebTestClient client;

    @Before
    public void before() {
        webClientBuild();
    }

    @After
    public void after() {
        clearMongo();
    }

    abstract void clearMongo();

    abstract void webClientBuild();

    Genre mockGenre() {
        return new Genre(MOCK_NAME);
    }

    Author mockAuthor() {
        return new Author(MOCK_NAME, MOCK_SURNAME);
    }

    Comment mockComment() {
        return new Comment(MOCK_NAME, MOCK_TEXT);
    }

    Book mockBook() {
        return new Book(MOCK_NAME, mockAuthorWithId(), mockGenreWithId(), MOCK_DESCRIPTION);
    }

    private Genre mockGenreWithId() {
        return new Genre(MOCK_ID, MOCK_NAME);
    }

    private Author mockAuthorWithId() {
        return new Author(MOCK_ID, MOCK_NAME, MOCK_SURNAME);
    }

}
