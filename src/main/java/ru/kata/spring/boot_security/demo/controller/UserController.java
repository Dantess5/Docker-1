package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.Util.UserValidator;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
public class UserController {
    private final UserService userServiceImpl;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl,
                           UserValidator userValidator) {
        this.userServiceImpl = userServiceImpl;
        this.userValidator = userValidator;
    }
    @GetMapping("/users/show")
    public String get(Model model) {
        User auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userServiceImpl.findUserById(auth.getId()));
//        model.addAttribute("listRoles", userServiceImpl.getUserRoles());
        return "users/show";
    }
}
