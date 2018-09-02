package com.github.pavelhe.model.mongodb.models;


import org.springframework.data.annotation.*;

public abstract class BaseModel {

    @Id
    protected String id;

    BaseModel() {

    }

    BaseModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
