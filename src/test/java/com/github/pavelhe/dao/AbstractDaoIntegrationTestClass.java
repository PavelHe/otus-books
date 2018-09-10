package com.github.pavelhe.dao;

import com.github.pavelhe.model.*;
import org.junit.runner.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit4.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractDaoIntegrationTestClass {

    private final static String TEST_FIELD_NAME = "test";

    public abstract void testCount() throws Exception;

    public abstract void testGetAll() throws Exception;

    public abstract void testGetById() throws Exception;

    public abstract void testGetByName() throws Exception;

    public abstract void testCreate() throws Exception;

    public abstract void testRemove() throws Exception;

    public abstract void testUpdate() throws Exception;

    protected Author mockAuthor() {
        return new Author(TEST_FIELD_NAME, TEST_FIELD_NAME);
    }

    protected Comment mockComment() {
        return new Comment(TEST_FIELD_NAME, TEST_FIELD_NAME);
    }

    protected Genre mockGenre() {
        return new Genre(TEST_FIELD_NAME);
    }

    protected Book mockBook() {
        return new Book(TEST_FIELD_NAME, mockExistAuthor(), mockExistGenre(), TEST_FIELD_NAME);
    }

    private Author mockExistAuthor() {
        return new Author(1L, TEST_FIELD_NAME, TEST_FIELD_NAME);
    }

    private Genre mockExistGenre() {
        return new Genre(1L, TEST_FIELD_NAME);
    }

}
