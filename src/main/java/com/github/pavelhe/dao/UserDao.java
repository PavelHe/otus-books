package com.github.pavelhe.dao;


import com.github.pavelhe.model.*;

public interface UserDao {

    User getById(Long id);

    User getByName(String name);

    void create(User user);

    void remove(Long id);

    void update(User user);

    boolean userAlreadyExist(String name);

}
