package com.github.pavelhe.shell;

import java.util.*;

import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import com.github.pavelhe.utils.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.shell.standard.*;

@ShellComponent
public class BookCommands {

    @Autowired
    private BookService bookService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private MessageSourceWrapper messageSource;

    @ShellMethod("Books count in DB")
    public String booksCount() {
        Long booksCount = bookService.count();
        return messageSource.getMessage("booksCount", new Object[]{booksCount});
    }

    @ShellMethod("Get all books")
    public String allBooks() {
        List<Book> books = bookService.getAll();
        return books.toString();
    }

    @ShellMethod("Get book by id")
    public String getBookById(@ShellOption Long id) {
        Book book = bookService.getById(id);
        return book.toString();
    }

    @ShellMethod("Create new book")
    public String createBook(@ShellOption String bookName, @ShellOption Long genreId,
                             @ShellOption Long authorId, @ShellOption String description) {
        Book book = new Book(bookName, description);
        bookService.create(book, authorId, genreId);
        return messageSource.getMessage("createBook");
    }

    @ShellMethod("Remove author by ID")
    public String removeBookById(@ShellOption Long bookId) {
        bookService.remove(bookId);
        return messageSource.getMessage("removeBookById", new Object[]{bookId});
    }

    @ShellMethod("Update book genre")
    public String updateBookGenre(@ShellOption Long bookId, @ShellOption Long newGenreId) {
        Book book = bookService.getById(bookId);
        Genre newGenre = genreService.getById(newGenreId);
        book.setGenre(newGenre);
        bookService.update(book);
        return messageSource.getMessage("updateBookGenre", new Object[]{newGenre.getName()});
    }

    @ShellMethod("Update book author")
    public String updateBookAuthor(@ShellOption Long bookId, @ShellOption Long newAuthorId) {
        Book book = bookService.getById(bookId);
        Author newAuthor = authorService.getById(newAuthorId);
        book.setAuthor(newAuthor);
        bookService.update(book);
        return messageSource.getMessage("updateBookAuthor", new Object[]{newAuthor.getName()});
    }

    @ShellMethod("Update name and description of book")
    public String updateBook(@ShellOption Long bookId, @ShellOption String bookName, @ShellOption String desc) {
        Book book = bookService.getById(bookId);
        book.setName(bookName);
        book.setDescription(desc);
        bookService.update(book);
        return messageSource.getMessage("updateBook");
    }

}
