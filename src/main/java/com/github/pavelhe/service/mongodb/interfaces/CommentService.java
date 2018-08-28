package com.github.pavelhe.service.mongodb.interfaces;


import java.util.*;

import com.github.pavelhe.model.mongodb.models.*;


public interface CommentService {

    Long count();

    List<Comment> getAll();

    Comment getById(Long id);

    List<Comment> getByName(String authorOfComment);

    void remove(Long id);

    void create(Comment comment);

    void update(Comment comment);

}
