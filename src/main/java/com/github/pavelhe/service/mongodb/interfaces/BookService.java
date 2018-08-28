package com.github.pavelhe.service.mongodb.interfaces;


import java.util.*;

import com.github.pavelhe.model.mongodb.models.*;


public interface BookService {

    Long count();

    List<Book> getAll();

    Book getById(Long id);

    Book getByName(String bookName);

    void remove(Long id);

    void create(Book book);

    void update(Book book);
}