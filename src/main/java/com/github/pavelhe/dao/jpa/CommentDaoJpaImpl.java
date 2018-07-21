package com.github.pavelhe.dao.jpa;

import java.util.*;
import javax.persistence.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Repository
public class CommentDaoJpaImpl implements CommentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long count() {
        return (long) entityManager.createQuery("FROM Comment c").getResultList().size();
    }

    @Override
    public List<Comment> getAll() {
        return entityManager.createQuery("FROM Comment c", Comment.class).getResultList();
    }

    @Override
    public Comment getById(Long id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    public List<Comment> getByName(String authorOfComment) {
        TypedQuery<Comment> byNameQuery = entityManager.createQuery("SELECT c FROM Comment c WHERE c.name=:authorOfComment", Comment.class);
        byNameQuery.setParameter("authorOfComment", authorOfComment);
        return byNameQuery.getResultList();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        entityManager.remove(getById(id));
    }

    @Override
    @Transactional
    public void create(Comment comment, Long bookId) {
        Book book = entityManager.find(Book.class, bookId);
        if (!comment.containsBook(book))
            comment.addBook(book);
        entityManager.persist(comment);
    }

    @Override
    @Transactional
    public void update(Comment comment) {
        entityManager.merge(comment);
    }
}
