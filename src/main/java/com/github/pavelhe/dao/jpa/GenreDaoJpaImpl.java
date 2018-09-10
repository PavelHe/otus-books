package com.github.pavelhe.dao.jpa;

import java.util.*;
import javax.persistence.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Repository
public class GenreDaoJpaImpl implements GenreDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(g) FROM Genre g", Long.class).getSingleResult();
    }

    @Override
    public List<Genre> getAll() {
        return entityManager.createQuery("FROM Genre g", Genre.class).getResultList();
    }

    @Override
    public Genre getById(Long id) {
        return entityManager.find(Genre.class, id);
    }

    @Override
    public Genre getByName(String genreName) {
        return QueryUtils.getEntityFromQuery("SELECT g FROM Genre g WHERE g.name=:genreName",
                Collections.singletonMap("genreName", genreName),
                Genre.class,
                entityManager);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        entityManager.remove(getById(id));
    }

    @Override
    @Transactional
    public void create(Genre genre) {
        entityManager.persist(genre);
    }

    @Override
    @Transactional
    public void update(Genre genre) {
        entityManager.merge(genre);
    }
}
