package br.com.quizmaster.quiz.rest.controller;

import br.com.quizmaster.quiz.model.QuizModel;
import br.com.quizmaster.quiz.rest.dto.QuizRequestDTO;
import br.com.quizmaster.quiz.rest.dto.QuizResponseDTO;
import br.com.quizmaster.quiz.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<QuizResponseDTO>> obterQuizzes() {
        List<QuizResponseDTO> quizResponseDTOS = quizService.obterQuizzes();
        return ResponseEntity.ok(quizResponseDTOS);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    public ResponseEntity<QuizResponseDTO> cadastrarQuiz(@Valid @RequestBody QuizRequestDTO quizRequestDTO, Authentication authentication) {
        QuizResponseDTO quizResponseDTO = quizService.cadastrarQuiz(quizRequestDTO, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(quizResponseDTO);
    }

    @PutMapping("/{idQuiz}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    public ResponseEntity<QuizResponseDTO> atualizarQuiz(@PathVariable Long idQuiz, @Valid @RequestBody QuizRequestDTO quizRequestDTO) {
        QuizResponseDTO quizResponseDTO = quizService.atualizarQuiz(idQuiz, quizRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(quizResponseDTO);
    }

    @DeleteMapping("/{idQuiz}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    public ResponseEntity<String> deletarQuiz(@PathVariable Long idQuiz) {
        quizService.deletarQuiz(idQuiz);
        return ResponseEntity.ok("Quiz com ID " + idQuiz + " deletado com sucesso.");
    }
}
