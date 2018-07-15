package com.github.pavelhe.shell;

import java.util.*;

import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import com.github.pavelhe.utils.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.shell.standard.*;

@ShellComponent
public class AuthorCommands {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private MessageSourceWrapper messageSource;


    @ShellMethod("Authors count in DB")
    public String authorsCount() {
        Long authorsCount = authorService.count();
        return messageSource.getMessage("authorsCount", new Object[]{authorsCount});
    }

    @ShellMethod("Get all authors")
    public String allAuthors() {
        List<Author> authors = authorService.getAll();
        return authors.toString();
    }

    @ShellMethod("Get author by id")
    public String getAuthorById(@ShellOption Long id) {
        Author author = authorService.getById(id);
        return author.toString();
    }

    @ShellMethod("Create new author")
    public String createAuthor(@ShellOption String authorName, @ShellOption String surname) {
        authorService.create(new Author(authorName, surname));
        return messageSource.getMessage("createAuthor", new Object[]{authorName});
    }

    @ShellMethod("Remove author by ID")
    public String removeAuthorById(@ShellOption Long authorId) {
        authorService.remove(authorId);
        return messageSource.getMessage("removeAuthorById", new Object[]{authorId});
    }

    @ShellMethod("Update author")
    public String updateAuthor(@ShellOption Long id, @ShellOption String newAuthorName,
                               @ShellOption String newAuthorSurname) {
        Author author = authorService.getById(id);
        author.setName(newAuthorName);
        author.setSurname(newAuthorSurname);
        authorService.update(author);
        return messageSource.getMessage("updateAuthor");
    }

}
