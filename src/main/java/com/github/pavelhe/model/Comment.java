package com.github.pavelhe.model;

import java.time.*;
import javax.persistence.*;

import org.springframework.data.mongodb.core.mapping.*;

@Entity
@Document
public class Comment extends NamedModel {

    private String text;
    @Column(name = "time_of_commit")
    private LocalDateTime timeOfCommit = LocalDateTime.now();
    @ManyToOne
    @DBRef
    private Book book;

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
