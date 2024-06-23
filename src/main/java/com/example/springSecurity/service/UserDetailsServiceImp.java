package com.example.springSecurity.service;

import com.example.springSecurity.model.UserSec;
import com.example.springSecurity.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private IUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ///Hay que convertir el UserSEC en UserDetails

        //Traigo el user de la DB
        UserSec usersec = userRepo.findUserEntityByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("El usuario " + username + " no fue encontrado"));

        ///Lista para los permisos y roles
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        ///Traigo roles para convertirlos en SGA
        usersec.getRolesList()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));///ROLE_ para rol, sin eso es un authority osea permiso


        ///Traer permisos y convertirlos en SimpleGrantedAuthority stream es secuencia de objetos para darle formatos y operaciones como en js
        usersec.getRolesList().stream() //Convierto la lista de roles en stream para usar flatmap
                .flatMap(role -> role.getPermissionsList().stream())/// mapeo la lista de roles para convertirlas de nuevo en stream y usar foreach
                ///Por cada permiso que encuentro lo agrego al authoritylist transofrmando en SGA
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName())));
        return new User(
                usersec.getUsername(),
                usersec.getPassword(),
                usersec.isEnabled(),
                usersec.isAccountNotExpired(),
                usersec.isCredentialNotExpired(),
                usersec.isAccountNotLocked(),
                authorityList
        );
    }
}
