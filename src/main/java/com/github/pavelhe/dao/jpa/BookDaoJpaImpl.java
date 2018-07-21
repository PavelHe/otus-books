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
        return (long) entityManager.createQuery("FROM Book b").getResultList().size();
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
    public Book getByName(String bookName) {
        TypedQuery<Book> byNameQuery = entityManager.createQuery("SELECT b FROM Book b WHERE b.name=:bookName", Book.class);
        byNameQuery.setParameter("bookName", bookName);
        return byNameQuery.getSingleResult();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        entityManager.remove(getById(id));
    }

    @Override
    @Transactional
    public void create(Book book, Long genreId, Long authorId) {
        book.setGenre(entityManager.find(Genre.class, genreId));
        book.setAuthor(entityManager.find(Author.class, authorId));
        entityManager.persist(book);
    }

    @Override
    @Transactional
    public void update(Book book) {
        entityManager.merge(book);
    }
}
