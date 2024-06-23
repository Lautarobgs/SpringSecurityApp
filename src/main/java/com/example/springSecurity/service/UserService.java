package com.example.springSecurity.service;

import com.example.springSecurity.model.UserSec;
import com.example.springSecurity.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepository userRepo;
    @Override
    public List findAll() {
        return userRepo.findAll();
    }

    @Override
    public Optional findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public UserSec save(UserSec userSec) {
        return userRepo.save(userSec);
    }

    @Override
    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserSec update(UserSec userSec) {
        return userRepo.save(userSec);
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
