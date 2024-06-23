package com.example.springSecurity.service;

import com.example.springSecurity.model.UserSec;


import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserSec> findAll();
    Optional<UserSec> findById(Long id);
    UserSec save(UserSec userSec);
    void deleteById(Long id);
    UserSec update(UserSec userSec);
    String encriptPassword(String password);
}


