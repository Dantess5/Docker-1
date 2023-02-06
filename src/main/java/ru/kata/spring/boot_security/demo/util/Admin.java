package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
public class Admin {

    private final UserService userService;

    @Autowired
    public Admin(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void addAdmin() {
        User user = new User("admin", "admin", 99, "admin");
        Role role = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        user.setRoles(List.of(role, roleUser));
        userService.saveUser(user);
    }
}
