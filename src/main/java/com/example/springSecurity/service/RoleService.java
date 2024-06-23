package com.example.springSecurity.service;

import com.example.springSecurity.model.Role;
import com.example.springSecurity.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService{
    @Autowired
    private IRoleRepository roleRepo;
    @Override
    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepo.findById(id);
    }

    @Override
    public Role save(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void deleteById(Long id) {
        roleRepo.deleteById(id);
    }

    @Override
    public Role update(Role role) {
        return roleRepo.save(role);
    }
}
