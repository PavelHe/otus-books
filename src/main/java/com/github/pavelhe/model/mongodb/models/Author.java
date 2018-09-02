package com.github.pavelhe.model.mongodb.models;

import java.util.*;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

@Document(collection = "authors")
public class Author extends NamedModel {

    private String surname;
    @DBRef
    @JsonIgnore
    private Set<Book> books = new HashSet<>();

    public Author() {
    }

    public Author(String name, String surname) {
        super(name);
        this.surname = surname;
    }

    public Author(String id, String name, String surname) {
        super(id, name);
        this.surname = surname;
    }

    public Author(Author author) {
        author.setId(getId());
        author.setName(getName());
        author.setSurname(getSurname());
        author.setBooks(getBooks());
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
