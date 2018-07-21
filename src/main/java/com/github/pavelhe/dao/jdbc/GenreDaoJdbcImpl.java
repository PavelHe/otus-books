package com.github.pavelhe.dao.jdbc;

import java.util.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.dao.jdbc.mappers.*;
import com.github.pavelhe.model.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.*;

@Repository
public class GenreDaoJdbcImpl implements GenreDao {

    private NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbcImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long count() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM genre", Collections.emptyMap(), Long.class);
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("SELECT * FROM genre", Collections.emptyMap(), new GenreMapper());
    }

    @Override
    public Genre getById(Long id) {
        return jdbc.queryForObject("SELECT * FROM genre WHERE id=:id", Collections.singletonMap("id", id), new GenreMapper());
    }

    @Override
    public Genre getByName(String genreName) {
        return jdbc.queryForObject("SELECT * FROM genre WHERE name=:name", Collections.singletonMap("name", genreName), new GenreMapper());
    }

    @Override
    public void remove(Long id) {
        jdbc.update("DELETE FROM genre WHERE id=:id", Collections.singletonMap("id", id));
    }

    @Override
    public void create(Genre genre) {
        jdbc.update("INSERT INTO genre(name) VALUES (:name)", Collections.singletonMap("name", genre.getName()));
    }

    @Override
    public void update(Genre genre) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        jdbc.update("UPDATE genre SET name=:name WHERE id=:id", params);
    }
}
