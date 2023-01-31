//package ru.kata.spring.boot_security.demo.service;
//
//import org.springframework.stereotype.Service;
//import ru.kata.spring.boot_security.demo.models.Role;
//import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
//
//import java.util.List;
//
//@Service
//public class RoleServiceImpl implements RoleService {
//
//    private final RoleRepository roleRepository;
//
//    public RoleServiceImpl(RoleRepository roleRepository) {
//        this.roleRepository = roleRepository;
//    }
//
//    @Override
//    public List<Role> getRoles() {
//        return roleRepository.findAll();
//    }
//
//    @Override
//    public void saveRole(Role role) {
//        roleRepository.save(role);
//    }
//
//    @Override
//    public void removeRoleById(Long id) {
//        roleRepository.deleteById(id);
//    }
//}
