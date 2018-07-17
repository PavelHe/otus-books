package com.github.pavelhe.dao.jdbc.mappers;


import java.sql.*;

import com.github.pavelhe.model.*;
import org.springframework.jdbc.core.*;


public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getLong("id"));
        genre.setName(resultSet.getString("name"));
        return genre;
    }
}
