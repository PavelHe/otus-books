package com.github.pavelhe.web;

import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(@Qualifier("authorServiceImpl") AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(value = "/author", method = RequestMethod.GET)
    public String getAllAuthors(Model model) {
        model.addAttribute("authorList", authorService.getAll());
        model.addAttribute("author", new Author());
        return "authors";
    }

    @RequestMapping(value = "/author/delete/{id}", method = RequestMethod.GET)
    public String removeAuthorById(@PathVariable("id") Long id) {
        authorService.remove(id);
        return "redirect:/author";
    }

    @RequestMapping(value = "/author/update/{id}", method = RequestMethod.GET)
    public String updateAuthor(@PathVariable("id") Long id, Model model) {
        model.addAttribute("authorList", authorService.getAll());
        model.addAttribute("author", authorService.getById(id));
        return "authors";
    }

    @RequestMapping(value = "/author/add", method = RequestMethod.POST)
    public String addAuthor(@ModelAttribute Author author) {
        if (author.getId() != null)
            authorService.update(author);
        else
            authorService.create(author);
        return "redirect:/author";
    }

}
