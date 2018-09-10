package com.github.pavelhe.service;


import com.github.pavelhe.model.security.*;

public interface RoleService {

    String USER_ROLE = "USER_ROLE";

    Role getByName(String roleName);

    void create(Role role);

}
