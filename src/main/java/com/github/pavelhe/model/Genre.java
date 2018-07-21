package com.github.pavelhe.model;

import java.util.*;
import javax.persistence.*;

@Entity
public class Genre extends NamedModel {

    @OneToMany(mappedBy = "genre", fetch = FetchType.EAGER)
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
