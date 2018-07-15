package com.github.pavelhe.dao.mappers;


import java.sql.*;

import com.github.pavelhe.model.*;
import org.springframework.jdbc.core.*;

import static com.github.pavelhe.dao.mappers.MapperConstance.*;

public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getLong(ID));
        genre.setName(resultSet.getString(NAME));
        return genre;
    }
}
