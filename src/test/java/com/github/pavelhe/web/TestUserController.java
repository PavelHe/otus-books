package com.github.pavelhe.web;


import com.github.pavelhe.dao.*;
import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;


import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestUserController extends AbstractControllerTestClass {

    @Autowired
    @Qualifier("testUserDaoJpa")
    private UserDao userDao;

    @Autowired
    @Qualifier("testRoleDaoJpa")
    private RoleDao roleDao;

    @Autowired
    @Qualifier("testPasswordEncoder")
    private BCryptPasswordEncoder encoder;


    @Override
    protected MockMvc buildMockMvc() {
        UserService userService = new UserServiceImpl(userDao, encoder);
        RoleService roleService = new RoleServiceImpl(roleDao);
        return MockMvcBuilders
                .standaloneSetup(new UserController(userService, roleService))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testDefaultUrl() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testLoginUrl() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testSignupGet() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", notNullValue()))
                .andExpect(view().name("signup"));
    }

    @Test
    public void testSignupUserExist() throws Exception {
        User user = userDao.getByName("Bob");

        mockMvc.perform(post("/signup").flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attribute("usernameExists", notNullValue()))
                .andExpect(view().name("signup"));
    }

    @Test
    public void testSigupNewUser() throws Exception {
        String username = "test";
        String password = "123456";
        User user = new User(username, password);

        mockMvc.perform(post("/signup").flashAttr("user", user))
                .andExpect(status().is3xxRedirection());

        user = userDao.getByName(username);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotEquals(password, user.getPassword());
    }
}
