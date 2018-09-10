package com.github.pavelhe.web;

import java.util.*;

import com.github.pavelhe.model.*;
import com.github.pavelhe.model.security.*;
import com.github.pavelhe.service.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String defaultUrl() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String goToSignUp(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(@ModelAttribute User user, Model model) {
        if (userService.userNameAlreadyExist(user.getName())) {
            model.addAttribute("usernameExists", true);
            return "signup";
        }
        Set<UserRole> userRoles = new HashSet<>();
        Role role = roleService.getByName(RoleService.USER_ROLE);
        userRoles.add(new UserRole(user, role));

        userService.create(user, userRoles);

        return "redirect:/";
    }

}
