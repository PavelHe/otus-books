package com.github.pavelhe.model;

import java.time.*;
import java.util.*;
import javax.persistence.*;

import org.springframework.data.mongodb.core.mapping.*;

@Entity
@Document
public class Comment extends NamedModel {

    private String text;
    @Column(name = "time_of_commit")
    private LocalDateTime timeOfCommit = LocalDateTime.now();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "comment_book",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    @DBRef
    private Set<Book> books = new HashSet<>();

    public Comment() {

    }

    public Comment(String text) {
        this.text = text;
    }

    public Comment(String name, String text) {
        super(name);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public LocalDateTime getTimeOfCommit() {
        return timeOfCommit;
    }

    public void setTimeOfCommit(LocalDateTime timeOfCommit) {
        this.timeOfCommit = timeOfCommit;
    }

    public void addBook(Book book) {
        if (Objects.nonNull(book))
            books.add(book);
    }

    public boolean containsBook(Book book) {
        return books.contains(book);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", timeOfCommit=" + timeOfCommit +
                ", id=" + id +
                '}';
    }
}
