package com.github.pavelhe.dao.jdbc;

import java.util.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.dao.jdbc.mappers.*;
import com.github.pavelhe.model.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.*;

@Repository
public class AuthorDaoJdbcImpl implements AuthorDao {

    private NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbcImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long count() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM author", Collections.emptyMap(), Long.class);
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("SELECT * FROM author", Collections.emptyMap(), new AuthorMapper());
    }

    @Override
    public Author getById(Long id) {
        return jdbc.queryForObject("SELECT * FROM author WHERE id=:id", Collections.singletonMap("id", id), new AuthorMapper());
    }

    @Override
    public Author getByName(String authorName) {
        return jdbc.queryForObject("SELECT * FROM author WHERE name=:name", Collections.singletonMap("name", authorName), new AuthorMapper());
    }

    @Override
    public void remove(Long id) {
        jdbc.update("DELETE FROM author WHERE id=:id", Collections.singletonMap("id", id));
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
