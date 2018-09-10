package com.github.pavelhe.dao.jpa;

import java.util.*;
import javax.persistence.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getByName(String name) {
        return QueryUtils.getEntityFromQuery("SELECT u FROM User u WHERE u.name=:username",
                Collections.singletonMap("username", name),
                User.class,
                entityManager);
    }

    @Override
    @Transactional
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        entityManager.remove(getById(id));
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public boolean userAlreadyExist(String name) {
        TypedQuery<User> byNameQuery = entityManager.createQuery("SELECT u FROM User u WHERE u.name=:username", User.class);
        byNameQuery.setParameter("username", name);
        return byNameQuery.getResultList().size() > 0;
    }
}
