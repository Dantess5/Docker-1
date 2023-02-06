package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.util.UserValidator;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;

@Controller
public class AdminController {
    private final UserServiceImpl userServiceImpl;
    private final UserValidator userValidator;


    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, UserValidator userValidator) {
        this.userServiceImpl = userServiceImpl;
        this.userValidator = userValidator;
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/admin/registration")
    public String registrationPage(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", userServiceImpl.getUserRoles());
        return "admin/registration";
    }

    @PostMapping("/admin/registration")
    public String perfomRegistration(@ModelAttribute("userAdd") @Valid User user
            , BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/users";
        }
        userServiceImpl.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users")
    public String getUsers(Model model,@ModelAttribute("user") @Valid User user
            , BindingResult bindingResult  ) {
        User auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", userServiceImpl.getAllUsers());
        model.addAttribute("person", userServiceImpl.findUserByUsername(auth.getUsername()));
        model.addAttribute("listRoles", userServiceImpl.getUserRoles());
        return "admin/users";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("userEdit", userServiceImpl.findUserById(id));
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