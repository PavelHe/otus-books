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
    @Qualifier("bookServiceImpl")
    private BookService bookService;
    @Autowired
    @Qualifier("genreServiceImpl")
    private GenreService genreService;
    @Autowired
    @Qualifier("authorServiceImpl")
    private AuthorService authorService;
    @Autowired
    private MessageSourceWrapper messageSource;

    @ShellMethod("Books count in DB")
    public String booksCount() {
        Long booksCount = bookService.count();
        return messageSource.getMessage("books.count", booksCount);
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

    @ShellMethod("Get book by name")
    public String getBookByName(@ShellOption String bookName) {
        return bookService.getByName(bookName).toString();
    }

    @ShellMethod("Create new book")
    public String createBook(@ShellOption String bookName, @ShellOption Long genreId,
                             @ShellOption Long authorId, @ShellOption String description) {
        Book book = new Book(bookName, description);
        book.setAuthor(authorService.getById(authorId));
        book.setGenre(genreService.getById(genreId));
        bookService.create(book);
        return messageSource.getMessage("create.book");
    }

    @ShellMethod("Remove book by ID")
    public String removeBookById(@ShellOption Long bookId) {
        bookService.remove(bookId);
        return messageSource.getMessage("remove.book.by.id", bookId);
    }

    @ShellMethod("Update book genre")
    public String updateBookGenre(@ShellOption Long bookId, @ShellOption Long newGenreId) {
        Book book = bookService.getById(bookId);
        Genre newGenre = genreService.getById(newGenreId);
        book.setGenre(newGenre);
        bookService.update(book);
        return messageSource.getMessage("update.book.genre", newGenre.getName());
    }

    @ShellMethod("Update book author")
    public String updateBookAuthor(@ShellOption Long bookId, @ShellOption Long newAuthorId) {
        Book book = bookService.getById(bookId);
        Author newAuthor = authorService.getById(newAuthorId);
        book.setAuthor(newAuthor);
        bookService.update(book);
        return messageSource.getMessage("update.book.author", newAuthor.getName());
    }

    @ShellMethod("Update name and description of book")
    public String updateBook(@ShellOption Long bookId, @ShellOption String bookName, @ShellOption String desc) {
        Book book = bookService.getById(bookId);
        book.setName(bookName);
        book.setDescription(desc);
        bookService.update(book);
        return messageSource.getMessage("update.book");
    }

}
