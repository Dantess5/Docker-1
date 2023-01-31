package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(User user);
    List<User> getAllUsers();
    User findUserById(Long id);
    void setUserRoles(User user);
    User findUserByUsername(String username);
    void deleteUser(Long id);
    void updateUser(Long id, User user);
}
