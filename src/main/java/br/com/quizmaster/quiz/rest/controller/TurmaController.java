package br.com.quizmaster.quiz.rest.controller;

import br.com.quizmaster.quiz.model.TurmaModel;
import br.com.quizmaster.quiz.model.UsuarioModel;
import br.com.quizmaster.quiz.rest.dto.TurmaDTO;
import br.com.quizmaster.quiz.rest.dto.UsuarioDTO;
import br.com.quizmaster.quiz.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turma")
public class TurmaController {
    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public ResponseEntity<List<TurmaDTO>> obterTurmas() {
        List<TurmaDTO> turmaDTOS = turmaService.obterTurmas();
        return ResponseEntity.ok(turmaDTOS);
    }

    @PostMapping
    public ResponseEntity<TurmaDTO> cadastrarTurma(@Valid @RequestBody TurmaModel turmaModel) {
        TurmaDTO turmaDTO = turmaService.cadastrarTurma(turmaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaDTO);
    }

    @PutMapping("/{idTurma}")
    public ResponseEntity<TurmaDTO> atualizarTurma(@PathVariable Long idTurma, @Valid @RequestBody TurmaModel turmaModel) {
        TurmaDTO turmaDTO = turmaService.atualizarTurma(idTurma, turmaModel);
        return ResponseEntity.status(HttpStatus.OK).body(turmaDTO);
    }

    @DeleteMapping("/{idTurma}")
    public ResponseEntity<String> deletarTurma(@PathVariable Long idTurma) {
        turmaService.deletarTurma(idTurma);
        return ResponseEntity.ok("Turma com ID " + idTurma + " deletado com sucesso.");
    }
}
