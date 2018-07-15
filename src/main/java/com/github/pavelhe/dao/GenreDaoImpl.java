package com.github.pavelhe.dao;

import java.util.*;

import com.github.pavelhe.dao.mappers.*;
import com.github.pavelhe.model.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.*;

@Repository
public class GenreDaoImpl implements GenreDao {

    private NamedParameterJdbcOperations jdbc;

    public GenreDaoImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long count() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM genre", new HashMap<>(), Long.class);
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("SELECT * FROM genre", new HashMap<>(), new GenreMapper());
    }

    @Override
    public Genre getById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject("SELECT * FROM genre WHERE id=:id", params, new GenreMapper());
    }

    @Override
    public void remove(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbc.update("DELETE FROM genre WHERE id=:id", params);
    }

    @Override
    public void create(Genre genre) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", genre.getName());
        jdbc.update("INSERT INTO genre(name) VALUES (:name)", params);
    }

    @Override
    public void update(Genre genre) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        jdbc.update("UPDATE genre SET name=:name WHERE id=:id", params);
    }
}
