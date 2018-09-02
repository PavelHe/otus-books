package com.github.pavelhe.service.mongodb;


import java.math.*;
import java.util.*;

import com.github.pavelhe.model.mongodb.models.*;
import com.github.pavelhe.service.mongodb.interfaces.*;
import org.springframework.data.mongodb.core.*;

import static com.github.pavelhe.service.mongodb.QueryUtils.emptyQuery;
import static com.github.pavelhe.service.mongodb.QueryUtils.findByValueQuery;
import static com.github.pavelhe.service.mongodb.QueryUtils.findEntityBy;

public class CommentServiceMongoImpl implements CommentService {

    private MongoOperations template;

    public CommentServiceMongoImpl(MongoOperations template) {
        this.template = template;
    }

    @Override
    public Long count() {
        return template.count(emptyQuery(), Comment.class);
    }

    @Override
    public List<Comment> getAll() {
        return template.findAll(Comment.class);
    }

    @Override
    public Comment getById(String id) {
        return findEntityBy("id", id, Comment.class, template);
    }

    @Override
    public List<Comment> getByName(String authorOfComment) {
        return template.find(findByValueQuery("name", authorOfComment), Comment.class);
    }

    @Override
    public void remove(String id) {
        template.findAndRemove(findByValueQuery("id", id), Comment.class);
    }

    @Override
    public void create(Comment comment) {
        template.save(comment);
    }

    @Override
    public void update(Comment comment) {
        template.save(comment);
    }

    @Override
    public void removeAll() {
        template.dropCollection(Comment.class);
    }
}
