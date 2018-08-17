package com.github.pavelhe.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.*;

import org.apache.commons.compress.utils.*;

import org.springframework.core.io.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.web.multipart.*;

@Entity
@Document
public class Book extends NamedModel {

    private String description;
    private MultipartFile photo;
    @ManyToOne
    @DBRef
    @NotNull
    private Author author;
    @ManyToOne
    @DBRef
    @NotNull
    private Genre genre;
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    @DBRef
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

    public String base64Photo() {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(getBytesFromPhotoFile());
    }

    private boolean photoIsPresent() {
        try {
            return photo != null && photo.getBytes().length > 0;
        } catch (IOException e) {
            throw new RuntimeException("Error getting bytes from MultipartFile");
        }
    }

    private byte[] getBytesFromPhotoFile() {
        try {
            if (!photoIsPresent()) {
                ClassPathResource res = new ClassPathResource("/static/img/file-empty-icon.png");
                return IOUtils.toByteArray(res.getInputStream());
            }
            return photo.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error getting bytes from MultipartFile");
        }
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
