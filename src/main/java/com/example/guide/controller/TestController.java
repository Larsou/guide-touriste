package com.example.guide.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController                      // indique à Spring que c'est un contrôleur REST
@RequestMapping("/api/test")         // route de base à /api/test
public class TestController {

    @GetMapping                     // GET http://localhost:8080/api/test
    public String hello() {
        return "API sécurisée ok !"; // renvoie un simple texte
    }
}
