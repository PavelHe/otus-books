package com.github.pavelhe.service;

import java.util.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import com.github.pavelhe.model.security.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(@Qualifier("userDaoImpl") UserDao userDao,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public User getByName(String name) {
        return userDao.getByName(name);
    }

    @Override
    public void create(User user, Set<UserRole> userRoles) {
        String enctypePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(enctypePassword);
        user.getUserRoles().addAll(userRoles);

        userDao.create(user);
    }

    @Override
    public void remove(Long id) {
        userDao.remove(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public boolean userNameAlreadyExist(String userName) {
        return userDao.userAlreadyExist(userName);
    }
}
