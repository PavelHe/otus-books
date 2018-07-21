package com.github.pavelhe.service;


import java.util.*;

import com.github.pavelhe.model.*;

public interface GenreService {

    Long count();

    List<Genre> getAll();

    Genre getById(Long id);

    Genre getByName(String genreName);

    void remove(Long id);

    void create(Genre genre);

    void update(Genre genre);

}
