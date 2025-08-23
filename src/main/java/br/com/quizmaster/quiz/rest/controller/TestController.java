package br.com.quizmaster.quiz.rest.controller;

import org.springframework.security.access.prepost.PreAuthorize; // Importe esta classe
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Olá! Você está em uma rota protegida (acessível por qualquer usuário logado)!";
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')") // Diz ao Spring Security: "Só permita se o usuário tiver a ADMIN"
    public String adminOnly() {
        return "Olá, Admin! Você está em uma rota super secreta!";
    }
}