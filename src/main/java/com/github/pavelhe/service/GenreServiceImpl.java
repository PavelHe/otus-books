package com.github.pavelhe.service;

import java.util.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreDao genreDao;

    public GenreServiceImpl(@Qualifier("genreDaoJpaImpl") GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Long count() {
        return genreDao.count();
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Genre getById(Long id) {
        return genreDao.getById(id);
    }

    @Override
    public Genre getByName(String genreName) {
        return genreDao.getByName(genreName);
    }

    @Override
    public void remove(Long id) {
        genreDao.remove(id);
    }

    @Override
    public void create(Genre genre) {
        genreDao.create(genre);
    }

    @Override
    public void update(Genre genre) {
        genreDao.update(genre);
    }
}
