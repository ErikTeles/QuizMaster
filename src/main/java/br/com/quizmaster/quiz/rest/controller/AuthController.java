package br.com.quizmaster.quiz.rest.controller;

import br.com.quizmaster.quiz.model.UsuarioModel;
import br.com.quizmaster.quiz.rest.dto.LoginRequestDTO;
import br.com.quizmaster.quiz.rest.dto.LoginResponseDTO;
import br.com.quizmaster.quiz.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UsuarioModel) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}