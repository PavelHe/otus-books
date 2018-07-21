package com.github.pavelhe.service;


import java.util.*;

import com.github.pavelhe.model.*;

public interface AuthorService {

    Long count();

    List<Author> getAll();

    Author getById(Long id);

    Author getByName(String authorName);

    void remove(Long id);

    void create(Author author);

    void update(Author author);

}
