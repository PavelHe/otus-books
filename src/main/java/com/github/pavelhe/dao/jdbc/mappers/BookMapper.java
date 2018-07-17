package com.github.pavelhe.dao.jdbc.mappers;


import java.sql.*;

import com.github.pavelhe.model.*;
import org.springframework.jdbc.core.*;


public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setName(resultSet.getString("book_name"));
        book.setDescription(resultSet.getString("description"));
        book.setGenre(new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name")));
        book.setAuthor(new Author(resultSet.getLong("author_id"), resultSet.getString("author_name"),
                resultSet.getString("author_surname")));

        return book;
    }
}
