package com.github.pavelhe.web.formatter;

import java.text.*;
import java.util.*;

import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.format.Formatter;
import org.springframework.stereotype.*;

@Service
public class GenreFormatter implements Formatter<Genre> {

    private final GenreService service;

    public GenreFormatter(@Qualifier("genreServiceImpl") GenreService service) {
        this.service = service;
    }

    @Override
    public Genre parse(String s, Locale locale) throws ParseException {
        return service.getById(Long.valueOf(s));
    }

    @Override
    public String print(Genre genre, Locale locale) {
        return genre.getId().toString();
    }
}
