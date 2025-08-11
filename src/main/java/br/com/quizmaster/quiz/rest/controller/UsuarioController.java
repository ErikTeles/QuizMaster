package br.com.quizmaster.quiz.rest.controller;

import br.com.quizmaster.quiz.model.UsuarioModel;
import br.com.quizmaster.quiz.rest.dto.UsuarioDTO;
import br.com.quizmaster.quiz.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> obterUsuarios() {
        List<UsuarioDTO> usuarioDTOS = usuarioService.obterUsuarios();
        return ResponseEntity.ok(usuarioDTOS);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@Valid @RequestBody UsuarioModel usuarioModel) {
        UsuarioDTO usuarioDTO = usuarioService.cadastrarUsuario(usuarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
    }

    @PutMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.idUsuario == #idUsuario")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long idUsuario, @Valid @RequestBody UsuarioModel usuarioModel) {
        UsuarioDTO usuarioDTO = usuarioService.atualizarUsuario(idUsuario, usuarioModel);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioDTO);
    }

    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.idUsuario == #idUsuario")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long idUsuario) {
        usuarioService.deletarUsuario(idUsuario);
        return ResponseEntity.ok("Usuário com ID " + idUsuario + " deletado com sucesso.");
    }
}
