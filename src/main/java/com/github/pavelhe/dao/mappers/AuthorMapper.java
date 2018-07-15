package com.github.pavelhe.dao.mappers;


import java.sql.*;

import com.github.pavelhe.model.*;
import org.springframework.jdbc.core.*;

import static com.github.pavelhe.dao.mappers.MapperConstance.*;

public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getLong(ID));
        author.setName(resultSet.getString(NAME));
        author.setSurname(resultSet.getString(SURNAME));
        return author;
    }
}
