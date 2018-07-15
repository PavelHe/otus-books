package com.github.pavelhe.service;

import java.util.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import org.springframework.stereotype.*;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Long count() {
        return authorDao.count();
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public Author getById(Long id) {
        return authorDao.getById(id);
    }

    @Override
    public void remove(Long id) {
        authorDao.remove(id);
    }

    @Override
    public void create(Author author) {
        authorDao.create(author);
    }

    @Override
    public void update(Author author) {
        authorDao.update(author);
    }
}
