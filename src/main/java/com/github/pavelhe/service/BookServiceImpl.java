package com.github.pavelhe.service;

import java.util.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import org.springframework.stereotype.*;

@Service
public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Long count() {
        return bookDao.count();
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Book getById(Long id) {
        return bookDao.getById(id);
    }

    @Override
    public void remove(Long id) {
        bookDao.remove(id);
    }

    @Override
    public void create(Book book, Long genreId, Long authorId) {
        bookDao.create(book, genreId, authorId);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }
}
