package com.github.pavelhe.service.mongodb.interfaces;


import java.math.*;
import java.util.*;

import com.github.pavelhe.model.mongodb.models.*;


public interface AuthorService {

    Long count();

    List<Author> getAll();

    Author getById(String id);

    Author getByName(String authorName);

    void remove(String id);

    void create(Author author);

    void update(Author author);

    void removeAll();

}
