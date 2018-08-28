package com.github.pavelhe.model.mongodb.models;

import java.io.*;
import java.util.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.compress.utils.*;
import org.springframework.core.io.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.web.multipart.*;

@Document
public class Book extends NamedModel {

    private String description;
    private MultipartFile photo;
    @DBRef
    @NotNull
    private Author author;
    @DBRef
    @NotNull
    private Genre genre;
    @DBRef
    @JsonIgnore
    private Set<Comment> comments = new LinkedHashSet<>();

    public Book() {
    }

    public Book(Long id, String name, Author author, Genre genre, String description) {
        super(id, name);
        this.author = author;
        this.genre = genre;
        this.description = description;
    }

    public Book(Book book) {
        book.setId(getId());
        book.setName(getName());
        book.setAuthor(getAuthor());
        book.setGenre(getGenre());
        book.setDescription(getDescription());
    }

    public Book(String name, String description) {
        super(name);
        this.description = description;
    }

    public Book(String name, Author author, Genre genre, String description) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author=" + author +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", description='" + description + '\'' +
                '}';
    }
}
