package com.github.pavelhe.dao.jpa;

import java.util.*;
import javax.persistence.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Repository
public class AuthorDaoJpaImpl implements AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(a) FROM Author a", Long.class).getSingleResult();
    }

    @Override
    public List<Author> getAll() {
        return entityManager.createQuery("FROM Author a", Author.class).getResultList();
    }

    @Override
    @Transactional
    public Author getById(Long id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public Author getByName(String authorName) {
        TypedQuery<Author> byNameQuery = entityManager.createQuery("SELECT a FROM Author a WHERE a.name=:authorName", Author.class);
        byNameQuery.setParameter("authorName", authorName);
        return byNameQuery.getSingleResult();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        entityManager.remove(getById(id));
    }

    @Override
    @Transactional
    public void create(Author author) {
        entityManager.persist(author);
    }

    @Override
    @Transactional
    public void update(Author author) {
        entityManager.merge(author);
    }
}
