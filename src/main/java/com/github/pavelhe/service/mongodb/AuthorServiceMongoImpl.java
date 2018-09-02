package com.github.pavelhe.service.mongodb;

import java.math.*;
import java.util.*;

import com.github.pavelhe.model.mongodb.models.*;
import com.github.pavelhe.service.mongodb.interfaces.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.stereotype.*;

import static com.github.pavelhe.service.mongodb.QueryUtils.emptyQuery;
import static com.github.pavelhe.service.mongodb.QueryUtils.findEntityBy;
import static com.github.pavelhe.service.mongodb.QueryUtils.findByValueQuery;

@Service
public class AuthorServiceMongoImpl implements AuthorService {

    private MongoOperations template;

    public AuthorServiceMongoImpl(MongoOperations template) {
        this.template = template;
    }

    @Override
    public Long count() {
        return template.count(emptyQuery(), Author.class);
    }

    @Override
    public List<Author> getAll() {
        return template.findAll(Author.class);
    }

    @Override
    public Author getById(String id) {
        return findEntityBy("id", id, Author.class, template);
    }

    @Override
    public Author getByName(String authorName) {
        return findEntityBy("name", authorName, Author.class, template);
    }

    @Override
    public void remove(String id) {
        template.findAndRemove(findByValueQuery("id", id), Author.class);
    }

    @Override
    public void create(Author author) {
        template.save(author);
    }

    @Override
    public void update(Author author) {
        template.save(author);
    }

    @Override
    public void removeAll() {
        template.dropCollection(Author.class);
    }
}
