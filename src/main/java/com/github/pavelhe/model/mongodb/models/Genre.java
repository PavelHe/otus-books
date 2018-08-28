package com.github.pavelhe.model.mongodb.models;

import java.util.*;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

@Document
public class Genre extends NamedModel {

    @DBRef
    @JsonIgnore
    private Set<Book> books = new HashSet<>();

    public Genre() {
    }

    public Genre(String name) {
        super(name);
    }

    public Genre(Long id, String name) {
        super(id, name);
    }

    public Genre(Genre genre) {
        genre.setId(getId());
        genre.setName(getName());
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
