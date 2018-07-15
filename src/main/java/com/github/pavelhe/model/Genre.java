package com.github.pavelhe.model;


public class Genre extends NamedModel {

    public Genre() {
    }

    public Genre(String name) {
        super(name);
    }

    public Genre(Long id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
