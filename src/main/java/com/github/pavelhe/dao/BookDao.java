package com.github.pavelhe.dao;


import java.util.*;

import com.github.pavelhe.model.*;

public interface BookDao {

    Long count();

    List<Book> getAll();

    Book getById(Long id);

    void remove(Long id);

    void create(Book book, Long genreId, Long authorId);

    void update(Book book);
}
