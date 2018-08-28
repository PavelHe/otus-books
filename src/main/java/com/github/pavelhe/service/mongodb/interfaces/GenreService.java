package com.github.pavelhe.service.mongodb.interfaces;


import java.util.*;

import com.github.pavelhe.model.mongodb.models.*;


public interface GenreService {

    Long count();

    List<Genre> getAll();

    Genre getById(Long id);

    Genre getByName(String genreName);

    void remove(Long id);

    void create(Genre genre);

    void update(Genre genre);

}
