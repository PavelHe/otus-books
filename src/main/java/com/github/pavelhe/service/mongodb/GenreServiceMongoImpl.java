package com.github.pavelhe.service.mongodb;


import java.math.*;
import java.util.*;


import com.github.pavelhe.model.mongodb.models.*;
import com.github.pavelhe.service.mongodb.interfaces.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.stereotype.*;

import static com.github.pavelhe.service.mongodb.QueryUtils.emptyQuery;
import static com.github.pavelhe.service.mongodb.QueryUtils.findByValueQuery;
import static com.github.pavelhe.service.mongodb.QueryUtils.findEntityBy;

@Service
public class GenreServiceMongoImpl implements GenreService {

    private MongoOperations template;

    public GenreServiceMongoImpl(MongoOperations template) {
        this.template = template;
    }


    @Override
    public Long count() {
        return template.count(emptyQuery(), Genre.class);
    }

    @Override
    public List<Genre> getAll() {
        return template.findAll(Genre.class);
    }

    @Override
    public Genre getById(String id) {
        return findEntityBy("id", id, Genre.class, template);
    }

    @Override
    public Genre getByName(String genreName) {
        return findEntityBy("name", genreName, Genre.class, template);
    }

    @Override
    public void remove(String id) {
        template.findAndRemove(findByValueQuery("id", id), Genre.class);
    }

    @Override
    public void create(Genre genre) {
        template.save(genre);
    }

    @Override
    public void update(Genre genre) {
        template.save(genre);
    }

    @Override
    public void removeAll() {
        template.dropCollection(Genre.class);
    }
}
