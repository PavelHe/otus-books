package com.github.pavelhe.dao;


import java.util.*;

import com.github.pavelhe.model.*;

public interface GenreDao {

    Long count();

    List<Genre> getAll();

    Genre getById(Long id);

    void remove(Long id);

    void create(Genre genre);

    void update(Genre genre);

}
