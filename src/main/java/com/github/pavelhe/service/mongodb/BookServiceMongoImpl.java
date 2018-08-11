package com.github.pavelhe.service.mongodb;


import java.util.*;

import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.stereotype.*;

import static com.github.pavelhe.service.mongodb.QueryUtils.emptyQuery;
import static com.github.pavelhe.service.mongodb.QueryUtils.findByValueQuery;
import static com.github.pavelhe.service.mongodb.QueryUtils.findEntityBy;

@Service
public class BookServiceMongoImpl implements BookService {

    private MongoTemplate template;

    public BookServiceMongoImpl(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public Long count() {
        return template.count(emptyQuery(), Book.class);
    }

    @Override
    public List<Book> getAll() {
        return template.findAll(Book.class);
    }

    @Override
    public Book getById(Long id) {
        return findEntityBy("id", id, Book.class, template);
    }

    @Override
    public Book getByName(String bookName) {
        return findEntityBy("name", bookName, Book.class, template);
    }

    @Override
    public void remove(Long id) {
        template.findAndRemove(findByValueQuery("id", id), Book.class);
    }

    @Override
    public void create(Book book, Long genreId, Long authorId) {
        template.save(book);
    }

    @Override
    public void create(Book book) {
        template.save(book);
    }

    @Override
    public void update(Book book) {
        template.save(book);
    }
}
