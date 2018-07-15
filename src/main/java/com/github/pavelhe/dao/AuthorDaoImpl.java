package com.github.pavelhe.dao;

import java.util.*;

import com.github.pavelhe.dao.mappers.*;
import com.github.pavelhe.model.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.*;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private NamedParameterJdbcOperations jdbc;

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long count() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM author", new HashMap<>(), Long.class);
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("SELECT * FROM author", new HashMap<>(), new AuthorMapper());
    }

    @Override
    public Author getById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject("SELECT * FROM author WHERE id=:id", params, new AuthorMapper());
    }

    @Override
    public void remove(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbc.update("DELETE FROM author WHERE id=:id", params);
    }

    @Override
    public void create(Author author) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", author.getName());
        params.put("surname", author.getSurname());
        jdbc.update("INSERT INTO author(name, surname) VALUES (:name, :surname)", params);
    }

    @Override
    public void update(Author author) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", author.getId());
        params.put("name", author.getName());
        params.put("surname", author.getSurname());
        jdbc.update("UPDATE author SET name=:name, surname=:surname WHERE id=:id", params);
    }
}
