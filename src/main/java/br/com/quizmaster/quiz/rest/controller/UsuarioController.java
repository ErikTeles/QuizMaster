package br.com.quizmaster.quiz.rest.controller;

import br.com.quizmaster.quiz.model.UsuarioModel;
import br.com.quizmaster.quiz.rest.dto.UsuarioDTO;
import br.com.quizmaster.quiz.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obterUsuarios() {
        List<UsuarioDTO> usuarioDTOS = usuarioService.obterUsuarios();
        return ResponseEntity.ok(usuarioDTOS);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@Valid @RequestBody UsuarioModel usuarioModel) {
        UsuarioDTO usuarioDTO = usuarioService.cadastrarUsuario(usuarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@Valid @RequestBody UsuarioModel usuarioModel) {
        UsuarioDTO usuarioDTO = usuarioService.atualizarUsuario(usuarioModel);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioDTO);
    }

    @DeleteMapping
    public ResponseEntity<String> deletarUsuario(@Valid @RequestBody UsuarioModel usuarioModel) {
        usuarioService.deletarUsuario(usuarioModel);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso.");
    }
}
