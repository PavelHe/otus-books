package com.github.pavelhe.web;


import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class GenreController {

    private GenreService genreService;

    public GenreController(@Qualifier("genreServiceImpl") GenreService genreService) {
        this.genreService = genreService;
    }

    @RequestMapping(value = "/genre", method = RequestMethod.GET)
    public String getAllAGenre(Model model) {
        model.addAttribute("genreList", genreService.getAll());
        model.addAttribute("genre", new Genre());
        return "genres";
    }

    @RequestMapping(value = "/genre/delete/{id}", method = RequestMethod.GET)
    public String removeGenre(@PathVariable("id") Long id) {
        genreService.remove(id);
        return "redirect:/genre";
    }

    @RequestMapping(value = "/genre/add", method = RequestMethod.POST)
    public String addGenre(@ModelAttribute Genre genre) {
        if (genre.getId() != null)
            genreService.update(genre);
        else
            genreService.create(genre);
        return "redirect:/genre";
    }

    @RequestMapping(value = "/genre/update/{id}", method = RequestMethod.GET)
    public String updateGenre(@PathVariable("id") Long id, Model model) {
        model.addAttribute("genreList", genreService.getAll());
        model.addAttribute("genre", genreService.getById(id));
        return "genres";
    }

}
