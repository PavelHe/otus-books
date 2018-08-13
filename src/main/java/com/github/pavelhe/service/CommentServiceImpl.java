package com.github.pavelhe.service;

import java.util.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import org.springframework.stereotype.*;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public Long count() {
        return commentDao.count();
    }

    @Override
    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    @Override
    public Comment getById(Long id) {
        return commentDao.getById(id);
    }

    @Override
    public List<Comment> getByName(String authorOfComment) {
        return commentDao.getByName(authorOfComment);
    }

    @Override
    public void remove(Long id) {
        commentDao.remove(id);
    }

    @Override
    public void create(Comment comment) {
        commentDao.create(comment);
    }

    @Override
    public void update(Comment comment) {
        commentDao.update(comment);
    }
}
