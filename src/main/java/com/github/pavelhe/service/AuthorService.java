package com.github.pavelhe.service;


import java.util.*;

import com.github.pavelhe.model.*;

public interface AuthorService {

    Long count();

    List<Author> getAll();

    Author getById(Long id);

    void remove(Long id);

    void create(Author author);

    void update(Author author);

}
