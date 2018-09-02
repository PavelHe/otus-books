package com.github.pavelhe.service.mongodb.interfaces;


import java.math.*;
import java.util.*;

import com.github.pavelhe.model.mongodb.models.*;


public interface BookService {

    Long count();

    List<Book> getAll();

    Book getById(String id);

    Book getByName(String bookName);

    void remove(String id);

    void create(Book book);

    void update(Book book);

    void removeAll();
}
