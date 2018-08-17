package com.github.pavelhe.web.formatter;


import java.text.*;
import java.util.*;

import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.format.Formatter;
import org.springframework.stereotype.*;

@Service
public class AuthorFormatter implements Formatter<Author> {

    private final AuthorService service;

    public AuthorFormatter(@Qualifier("authorServiceImpl") AuthorService service) {
        this.service = service;
    }

    @Override
    public Author parse(String s, Locale locale) throws ParseException {
        return service.getById(Long.valueOf(s));
    }

    @Override
    public String print(Author author, Locale locale) {
        return author.getId().toString();
    }
}
