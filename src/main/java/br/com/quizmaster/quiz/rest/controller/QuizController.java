package br.com.quizmaster.quiz.rest.controller;

import br.com.quizmaster.quiz.model.QuizModel;
import br.com.quizmaster.quiz.rest.dto.QuizResponseDTO;
import br.com.quizmaster.quiz.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<List<QuizResponseDTO>> obterQuizzes() {
        List<QuizResponseDTO> quizResponseDTOS = quizService.obterQuizzes();
        return ResponseEntity.ok(quizResponseDTOS);
    }

    @PostMapping
    public ResponseEntity<QuizResponseDTO> cadastrarQuiz(@Valid @RequestBody QuizModel quizModel) {
        QuizResponseDTO quizResponseDTO = quizService.cadastrarQuiz(quizModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(quizResponseDTO);
    }

    @PutMapping("/{idQuiz}")
    public ResponseEntity<QuizResponseDTO> atualizarQuiz(@PathVariable Long idQuiz, @Valid @RequestBody QuizModel quizModel) {
        QuizResponseDTO quizResponseDTO = quizService.atualizarQuiz(idQuiz, quizModel);
        return ResponseEntity.status(HttpStatus.OK).body(quizResponseDTO);
    }

    @DeleteMapping("/{idQuiz}")
    public ResponseEntity<String> deletarQuiz(@PathVariable Long idQuiz) {
        quizService.deletarQuiz(idQuiz);
        return ResponseEntity.ok("Quiz com ID " + idQuiz + " deletado com sucesso.");
    }
}
