package com.github.pavelhe.dao;


import com.github.pavelhe.model.security.*;

public interface RoleDao {

    Role getByName(String roleName);

    void create(Role role);

}
