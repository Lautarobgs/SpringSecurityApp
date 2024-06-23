package com.example.springSecurity.controller;

import com.example.springSecurity.model.Permission;
import com.example.springSecurity.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    @Autowired
    private IPermissionService permServ;

    @GetMapping
    public ResponseEntity<List> getAllPermissions(){
        List permissions = permServ.findAll();
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPermissionById(@PathVariable Long id) {
        Optional<Permission> permission = permServ.findById(id);
        return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); // :: ref a metodo, el lambda crea un response entity

    }

    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission){
        Permission newPermission = permServ.save(permission);
        return ResponseEntity.ok(newPermission);
    }


}
