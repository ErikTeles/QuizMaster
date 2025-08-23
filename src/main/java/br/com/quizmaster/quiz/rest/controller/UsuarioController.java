package br.com.quizmaster.quiz.rest.controller;

import br.com.quizmaster.quiz.model.UsuarioModel;
import br.com.quizmaster.quiz.rest.dto.UsuarioResponseDTO;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> obterUsuarios() {
        List<UsuarioResponseDTO> usuarioResponseDTOS = usuarioService.obterUsuarios();
        return ResponseEntity.ok(usuarioResponseDTOS);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@Valid @RequestBody UsuarioModel usuarioModel) {
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.cadastrarUsuario(usuarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponseDTO);
    }

    @PutMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.idUsuario == #idUsuario")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long idUsuario, @Valid @RequestBody UsuarioModel usuarioModel) {
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.atualizarUsuario(idUsuario, usuarioModel);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioResponseDTO);
    }

    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.idUsuario == #idUsuario")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long idUsuario) {
        usuarioService.deletarUsuario(idUsuario);
        return ResponseEntity.ok("Usuário com ID " + idUsuario + " deletado com sucesso.");
    }
}
