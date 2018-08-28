package com.github.pavelhe.model.mongodb.models;

import javax.persistence.*;
import javax.validation.constraints.*;

@MappedSuperclass
public abstract class NamedModel extends BaseModel {

    @NotNull
    protected String name;

    public NamedModel() {

    }

    public NamedModel(Long id, String name) {
        super(id);
        this.name = name;
    }

    public NamedModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NamedModel that = (NamedModel) o;

        return (name != null ? name.equals(that.name) : that.name == null) && (id != null ? id.equals(that.id) : that.id == null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
