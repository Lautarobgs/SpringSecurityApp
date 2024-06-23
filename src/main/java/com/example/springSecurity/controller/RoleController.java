package com.example.springSecurity.controller;


import com.example.springSecurity.model.Permission;
import com.example.springSecurity.model.Role;
import com.example.springSecurity.service.IPermissionService;
import com.example.springSecurity.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @GetMapping
    public ResponseEntity<List> getAllRoles() {
        List roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity createRole(@RequestBody Role role) {
        Set<Permission> permissionList = new HashSet<Permission>(); ///set auxiliar para filtrar los permisos
        Permission readPermission;

        // Recuperar la Permission/s por su ID, filtro de permisos por si se tipea mal alguno chequea si existe en la db
        for (Permission per : role.getPermissionsList()) {
            readPermission = permissionService.findById(per.getId()).orElse(null);
            if (readPermission != null) {
                //si encuentro, guardo en la lista
                permissionList.add(readPermission);
            }
        }

        role.setPermissionsList(permissionList);
        Role newRole = roleService.save(role);
        return ResponseEntity.ok(newRole);
    }
}

