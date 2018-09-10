package com.github.pavelhe.dao.jpa;

import java.util.*;
import javax.persistence.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Repository
public class BookDaoJpaImpl implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(b) FROM Book b", Long.class).getSingleResult();
    }

    @Override
    public List<Book> getAll() {
        return entityManager.createQuery("FROM Book b", Book.class).getResultList();
    }

    @Override
    public Book getById(Long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    @Transactional
    public Book getByName(String bookName) {
        return QueryUtils.getEntityFromQuery("SELECT b FROM Book b WHERE b.name=:bookName",
                Collections.singletonMap("bookName", bookName),
                Book.class,
                entityManager);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        entityManager.remove(getById(id));
    }

    @Override
    @Transactional
    public void create(Book book, Long genreId, Long authorId) {
        entityManager.persist(book);
    }

    @Override
    @Transactional
    public void create(Book book) {
        create(book, null, null);
    }

    @Override
    @Transactional
    public void update(Book book) {
        entityManager.merge(book);
    }
}
