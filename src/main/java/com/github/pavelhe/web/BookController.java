package com.github.pavelhe.web;

import java.io.*;
import java.util.*;
import javax.validation.*;

import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

@Controller
public class BookController {

    private BookService bookService;
    private AuthorService authorService;
    private GenreService genreService;

    public BookController(@Qualifier("bookServiceImpl") BookService bookService,
                          @Qualifier("authorServiceImpl") AuthorService authorService,
                          @Qualifier("genreServiceImpl") GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @RequestMapping(value = {"/", "/book"}, method = RequestMethod.GET)
    public String getAllBooks(Model model) {
        model.addAttribute("bookList", bookService.getAll());
        model.addAttribute("authorList", authorService.getAll());
        model.addAttribute("genreList", genreService.getAll());
        model.addAttribute("book", new Book());
        return "books";
    }

    @RequestMapping(value = "/book/delete/{id}", method = RequestMethod.GET)
    public String removeBook(@PathVariable("id") Long id) {
        bookService.remove(id);
        return "redirect:/book";
    }

    @RequestMapping(value = "/book/add", method = RequestMethod.POST, headers = "content-type=multipart/*")
    public String addBook(@ModelAttribute @Valid Book book, MultipartFile photo, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("addError", true);
            return "redirect:/book";
        }
        if (book.getId() != null)
            bookService.update(book);
        else
            bookService.create(book);
        return "redirect:/book";
    }

    @RequestMapping(value = "/book/update/{id}", method = RequestMethod.GET)
    public String updateBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookService.getById(id));
        return "books";
    }

    @RequestMapping(value = "/book/info/{id}", method = RequestMethod.GET)
    public String getInformationAboutBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("authorList", authorService.getAll());
        model.addAttribute("genreList", genreService.getAll());
        model.addAttribute("comment", new Comment());
        return "bookInfo";
    }

}
