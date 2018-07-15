package com.github.pavelhe.dao.mappers;


import java.sql.*;

import com.github.pavelhe.model.*;
import org.springframework.jdbc.core.*;

import static com.github.pavelhe.dao.mappers.MapperConstance.*;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong(ID));
        book.setName(resultSet.getString(BOOK_NAME));
        book.setDescription(resultSet.getString(DESCRIPTION));
        book.setGenre(new Genre(resultSet.getLong(GENRE_ID), resultSet.getString(GENRE_NAME)));
        book.setAuthor(new Author(resultSet.getLong(AUTHOR_ID), resultSet.getString(AUTHOR_NAME),
                resultSet.getString(AUTHOR_SURNAME)));

        return book;
    }
}
