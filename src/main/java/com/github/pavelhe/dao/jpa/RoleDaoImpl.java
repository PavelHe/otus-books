package com.github.pavelhe.dao.jpa;

import javax.persistence.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.security.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getByName(String roleName) {
        TypedQuery<Role> byNamQuery = entityManager.createQuery("SELECT r FROM Role r WHERE r.name=:roleName", Role.class);
        byNamQuery.setParameter("roleName", roleName);
        return byNamQuery.getSingleResult();
    }

    @Override
    @Transactional
    public void create(Role role) {
        entityManager.persist(role);
    }
}
