package com.github.pavelhe.service;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.security.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleServiceImpl(@Qualifier("roleDaoImpl") RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role getByName(String roleName) {
        return roleDao.getByName(roleName);
    }

    @Override
    public void create(Role role) {
        roleDao.create(role);
    }
}
