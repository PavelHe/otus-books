package com.github.pavelhe.model.mongodb.models;

import java.time.*;
import java.time.format.*;

import org.springframework.data.mongodb.core.mapping.*;

@Document
public class Comment extends NamedModel {

    private String text;
    private LocalDateTime timeOfCommit = LocalDateTime.now();
    @DBRef
    private Book book;

    public Comment() {
        name = "Anonymous";
    }

    public Comment(String text) {
        this();
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getTimeOfCommit() {
        return timeOfCommit;
    }

    public void setTimeOfCommit(LocalDateTime timeOfCommit) {
        this.timeOfCommit = timeOfCommit;
    }

    public String timeOfCommitToString() {
        return timeOfCommit.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
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
