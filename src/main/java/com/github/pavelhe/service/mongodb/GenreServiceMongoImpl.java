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
public class GenreServiceMongoImpl implements GenreService {

    private MongoTemplate template;

    public GenreServiceMongoImpl(MongoTemplate template) {
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
    public Genre getById(Long id) {
        return findEntityBy("id", id, Genre.class, template);
    }

    @Override
    public Genre getByName(String genreName) {
        return findEntityBy("name", genreName, Genre.class, template);
    }

    @Override
    public void remove(Long id) {
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
}
