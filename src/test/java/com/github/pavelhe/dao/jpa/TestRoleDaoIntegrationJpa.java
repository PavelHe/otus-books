package com.github.pavelhe.dao.jpa;

import com.github.pavelhe.config.*;
import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.security.*;
import com.github.pavelhe.service.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.springframework.transaction.annotation.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestBasicConfiguration.class, TestJPAConfiguration.class})
@Transactional(value = "testJpaTransactionManager")
public class TestRoleDaoIntegrationJpa {

    @Autowired
    @Qualifier("testRoleDaoJpa")
    private RoleDao roleDao;

    @Test
    public void testGetByName() throws Exception {
        Role role = roleDao.getByName(RoleService.USER_ROLE);
        assertNotNull(role);
        assertNotNull(role.getRoleId());
        assertEquals(RoleService.USER_ROLE, role.getName());
    }

    @Test
    public void testCreate() throws Exception {
        String mockRole = "MOCK_ROLE";
        Role role = new Role();
        role.setName(mockRole);
        roleDao.create(role);
        role = roleDao.getByName(mockRole);
        assertNotNull(role);
        assertNotNull(role.getRoleId());
        assertEquals(mockRole, role.getName());
    }

}
