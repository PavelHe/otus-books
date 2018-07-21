package com.github.pavelhe.dao;


import java.util.*;

import com.github.pavelhe.model.*;

public interface CommentDao {

    Long count();

    List<Comment> getAll();

    Comment getById(Long id);

    List<Comment> getByName(String authorOfComment);

    void remove(Long id);

    void create(Comment comment, Long bookId);

    void update(Comment comment);
}
