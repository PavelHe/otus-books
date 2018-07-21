package com.github.pavelhe.model;

import java.util.*;
import javax.persistence.*;

@Entity
public class Book extends NamedModel {

    @ManyToOne
    private Author author;
    @ManyToOne
    private Genre genre;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "comment_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private Set<Comment> comments = new HashSet<>();
    private String description;

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
