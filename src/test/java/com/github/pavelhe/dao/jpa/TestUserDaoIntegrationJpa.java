package com.github.pavelhe.dao.jpa;

import com.github.pavelhe.config.*;
import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
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
public class TestUserDaoIntegrationJpa {

    @Autowired
    @Qualifier("testUserDaoJpa")
    private UserDao userDao;

    @Test
    public void testGetById() throws Exception {
        User user = userDao.getById(1L);
        assertNotNull(user);
    }

    @Test
    public void testGetByName() throws Exception {
        String userName = "Bob";
        User user = userDao.getByName(userName);
        assertNotNull(user);
        assertEquals(userName, user.getUsername());
    }

    @Test
    public void testCreate() throws Exception {
        String userName = "test";
        String password = "123";
        User newUser = new User(userName, password);
        userDao.create(newUser);
        newUser = userDao.getByName(userName);
        assertNotNull(newUser);
        assertNotNull(newUser.getId());
        assertEquals(userName, newUser.getUsername());
        assertEquals(password, newUser.getPassword());
    }

    @Test
    public void testRemove() throws Exception {
        assertNotNull(userDao.getById(1L));
        userDao.remove(1L);
        assertNull(userDao.getById(1L));
    }

    @Test
    public void testUpdate() throws Exception {
        String mockName = "mock name";
        User user = userDao.getByName("Bob");
        assertNotNull(user);
        user.setName(mockName);
        userDao.update(user);
        user = userDao.getByName(mockName);
        assertNotNull(user);
        assertEquals(mockName, user.getName());
    }

    @Test
    public void testUserAlreadyExist() throws Exception {
        boolean trueCondition = userDao.userAlreadyExist("Bob");
        assertTrue(trueCondition);
    }

}
