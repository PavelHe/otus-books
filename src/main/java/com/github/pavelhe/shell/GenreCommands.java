package com.github.pavelhe.shell;

import java.util.*;

import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import com.github.pavelhe.utils.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.shell.standard.*;

@ShellComponent
public class GenreCommands {

    @Autowired
    private GenreService genreService;
    @Autowired
    private MessageSourceWrapper messageSource;


    @ShellMethod("Genre count in DB")
    public String genresCount() {
        Long genreCount = genreService.count();
        return messageSource.getMessage("genresCount", new Object[]{genreCount});
    }

    @ShellMethod("Get all genres")
    public String allGenres() {
        List<Genre> genres = genreService.getAll();
        return genres.toString();
    }

    @ShellMethod("Get genre by id")
    public String getGenreById(@ShellOption Long id) {
        Genre genre = genreService.getById(id);
        return genre.toString();
    }

    @ShellMethod("Create new genre")
    public String createGenre(@ShellOption String genreName) {
        genreService.create(new Genre(genreName));
        return messageSource.getMessage("createGenre", new Object[]{genreName});
    }

    @ShellMethod("Remove genre by ID")
    public String removeGenreById(@ShellOption Long genreId) {
        genreService.remove(genreId);
        return messageSource.getMessage("removeGenreById", new Object[]{genreId});
    }

    @ShellMethod("Update genre")
    public String updateGenre(@ShellOption Long id, @ShellOption String newGenreName) {
        Genre genre = genreService.getById(id);
        genre.setName(newGenreName);
        genreService.update(genre);
        return messageSource.getMessage("updateGenre", new Object[]{newGenreName});
    }

}
