package com.github.pavelhe.service.mongodb.interfaces;


import java.math.*;
import java.util.*;

import com.github.pavelhe.model.mongodb.models.*;


public interface CommentService {

    Long count();

    List<Comment> getAll();

    Comment getById(String id);

    List<Comment> getByName(String authorOfComment);

    void remove(String id);

    void create(Comment comment);

    void update(Comment comment);

    void removeAll();

}
