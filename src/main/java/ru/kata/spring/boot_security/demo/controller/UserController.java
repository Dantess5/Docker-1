package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Util.UserValidator;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final UserValidator userValidator;


    @Autowired
    public UserController(UserServiceImpl userServiceImpl,
                           UserValidator userValidator) {
        this.userServiceImpl = userServiceImpl;
        this.userValidator = userValidator;
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }



    @GetMapping("/users/show")
    public String get(Model model) {
        User auth =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userServiceImpl.findUserById(auth.getId()));
        return "users/show";
    }

    @GetMapping("/admin/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "admin/registration";
    }

    @PostMapping("/admin/registration")
    public String perfomRegistration(@ModelAttribute("user") @Valid User user
            , BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()) {
            return "/admin/registration";
        }
        Role role = new Role("ROLE_USER");
        user.setRoles(List.of(role));
        userServiceImpl.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userServiceImpl.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userServiceImpl.findUserById(id));
        return "admin/edit";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") User user
            , @PathVariable("id") Long id) {
        userServiceImpl.updateUser(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") Long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin/users";
    }
}