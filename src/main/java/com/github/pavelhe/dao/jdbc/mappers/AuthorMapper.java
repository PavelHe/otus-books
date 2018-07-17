package com.github.pavelhe.dao.jdbc.mappers;


import java.sql.*;

import com.github.pavelhe.model.*;
import org.springframework.jdbc.core.*;


public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getLong("id"));
        author.setName(resultSet.getString("name"));
        author.setSurname(resultSet.getString("surname"));
        return author;
    }
}
