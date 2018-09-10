package com.github.pavelhe.service;


import java.util.*;

import com.github.pavelhe.model.*;
import com.github.pavelhe.model.security.*;

public interface UserService {

    User getById(Long id);

    User getByName(String name);

    void create(User user, Set<UserRole> userRoles);

    void remove(Long id);

    void update(User user);

    boolean userNameAlreadyExist(String userName);

}
