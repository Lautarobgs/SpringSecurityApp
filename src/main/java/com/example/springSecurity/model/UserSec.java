package com.example.springSecurity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserSec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private boolean enabled;
    private boolean accountNotExpired;
    private boolean accountNotLocked;
    private boolean credentialNotExpired;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles", ///Nombre de la tabla relacionada n/n
            joinColumns = @JoinColumn(name = "user_id"),/// fk user
            inverseJoinColumns = @JoinColumn(name = "role_id"))///fk rol
    private Set<Role> rolesList = new HashSet<>();
}
