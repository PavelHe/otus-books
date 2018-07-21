package com.github.pavelhe.model;

import java.util.*;
import javax.persistence.*;

@Entity
public class Author extends NamedModel {

    private String surname;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<>();

    public Author() {
    }

    public Author(String name, String surname) {
        super(name);
        this.surname = surname;
    }

    public Author(Long id, String name, String surname) {
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

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
