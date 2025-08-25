package br.com.quizmaster.quiz.rest.controller;

import br.com.quizmaster.quiz.model.TurmaModel;
import br.com.quizmaster.quiz.rest.dto.TurmaRequestDTO;
import br.com.quizmaster.quiz.rest.dto.TurmaResponseDTO;
import br.com.quizmaster.quiz.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turma")
public class TurmaController {
    @Autowired
    private TurmaService turmaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    public ResponseEntity<List<TurmaResponseDTO>> obterTurmas() {
        List<TurmaResponseDTO> turmaResponseDTOS = turmaService.obterTurmas();
        return ResponseEntity.ok(turmaResponseDTOS);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    public ResponseEntity<TurmaResponseDTO> cadastrarTurma(@Valid @RequestBody TurmaRequestDTO turmaRequestDTO, Authentication authentication) {
        TurmaResponseDTO turmaResponseDTO = turmaService.cadastrarTurma(turmaRequestDTO, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaResponseDTO);
    }

    @PutMapping("/{idTurma}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    public ResponseEntity<TurmaResponseDTO> atualizarTurma(@PathVariable Long idTurma, @Valid @RequestBody TurmaModel turmaModel) {
        TurmaResponseDTO turmaResponseDTO = turmaService.atualizarTurma(idTurma, turmaModel);
        return ResponseEntity.status(HttpStatus.OK).body(turmaResponseDTO);
    }

    @DeleteMapping("/{idTurma}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    public ResponseEntity<String> deletarTurma(@PathVariable Long idTurma) {
        turmaService.deletarTurma(idTurma);
        return ResponseEntity.ok("Turma com ID " + idTurma + " deletada com sucesso.");
    }
}
