package com.github.pavelhe.dao;


import java.util.*;

import com.github.pavelhe.model.*;

public interface AuthorDao {

    Long count();

    List<Author> getAll();

    Author getById(Long id);

    void remove(Long id);

    void create(Author author);

    void update(Author author);

}
