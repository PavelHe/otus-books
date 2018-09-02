package com.github.pavelhe.service.mongodb.interfaces;


import java.math.*;
import java.util.*;

import com.github.pavelhe.model.mongodb.models.*;


public interface GenreService {

    Long count();

    List<Genre> getAll();

    Genre getById(String id);

    Genre getByName(String genreName);

    void remove(String id);

    void create(Genre genre);

    void update(Genre genre);

    void removeAll();

}
