package com.github.pavelhe.dao.jdbc;

import java.util.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.dao.jdbc.mappers.*;
import com.github.pavelhe.model.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.*;

@Repository
public class BookDaoJdbcImpl implements BookDao {

    private NamedParameterJdbcOperations jdbc;

    public BookDaoJdbcImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long count() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM book", Collections.emptyMap(), Long.class);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("SELECT b.id, b.name AS book_name, b.description, g.id AS genre_id, g.name AS genre_name, " +
                "a.id AS author_id, a.name AS author_name, a.surname AS author_surname \n" +
                "FROM book AS b \n" +
                "INNER JOIN genre g ON g.id=b.genre_id\n" +
                "INNER JOIN author a ON a.id=b.author_id;", Collections.emptyMap(), new BookMapper());
    }

    @Override
    public Book getById(Long id) {
        return jdbc.queryForObject("SELECT b.id, b.name AS book_name, b.description, g.id AS genre_id, g.name AS genre_name, " +
                "a.id AS author_id, a.name AS author_name, a.surname AS author_surname \n" +
                "FROM book AS b \n" +
                "INNER JOIN genre g ON g.id=b.genre_id\n" +
                "INNER JOIN author a ON a.id=b.author_id WHERE b.id=:id;", Collections.singletonMap("id", id), new BookMapper());
    }

    @Override
    public void remove(Long id) {
        jdbc.update("DELETE FROM book WHERE id=:id", Collections.singletonMap("id", id));
    }

    @Override
    public void create(Book book, Long genreId, Long authorId) {
        Author author = jdbc.queryForObject("SELECT * FROM author WHERE id=:id", Collections.singletonMap("id", authorId),
                new AuthorMapper());
        book.setAuthor(author);
        Genre genre = jdbc.queryForObject("SELECT * FROM genre WHERE id=:id", Collections.singletonMap("id", genreId),
                new GenreMapper());
        book.setGenre(genre);

        Map<String, Object> params = new HashMap<>();
        params.put("name", book.getName());
        params.put("genre_id", genre.getId());
        params.put("author_id", author.getId());
        params.put("description", book.getDescription());
        jdbc.update("INSERT INTO book (name, genre_id, author_id, description) " +
                "VALUES(:name, :genre_id, :author_id, :description)", params);
    }

    @Override
    public void update(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("name", book.getName());
        params.put("genre_id", book.getGenre().getId());
        params.put("author_id", book.getAuthor().getId());
        params.put("description", book.getDescription());

        jdbc.update("UPDATE book SET name=:name, genre_id=:genre_id, author_id=:author_id, description=:description" +
                " WHERE id=:id", params);
    }
}
