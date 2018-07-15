package com.github.pavelhe.model;


public class Author extends NamedModel {

    private String surname;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
