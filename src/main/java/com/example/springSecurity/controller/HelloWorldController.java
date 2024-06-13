package com.example.springSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/holaseg")
    public String secHelloWorld(){
        return "hola seguridad";
    }

    @GetMapping("/holanoseg")
    public String nosecHelloWorld(){
        return "hola inseguridad";
    }

}
