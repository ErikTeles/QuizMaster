package br.com.quizmaster.quiz.rest.controller;

import br.com.quizmaster.quiz.model.QuestaoModel;
import br.com.quizmaster.quiz.model.QuizModel;
import br.com.quizmaster.quiz.rest.dto.QuestaoRequestDTO;
import br.com.quizmaster.quiz.rest.dto.QuestaoResponseDTO;
import br.com.quizmaster.quiz.rest.dto.QuizResponseDTO;
import br.com.quizmaster.quiz.service.QuestaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questao")
public class QuestaoController {
    @Autowired
    private QuestaoService questaoService;

    @GetMapping("/quiz/{quizId}")
    @PreAuthorize("isAuthenticated()") // Qualquer usuário logado pode ver as questões de um quiz
    public ResponseEntity<List<QuestaoResponseDTO>> buscarQuestoesPorQuiz(@PathVariable Long quizId) {
        List<QuestaoResponseDTO> questaoResponseDTOS = questaoService.buscarQuestoesPorQuiz(quizId);
        return ResponseEntity.ok(questaoResponseDTOS);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')") // Apenas Admin e Professor podem criar questões
    public ResponseEntity<QuestaoResponseDTO> cadastrarQuestao(@Valid @RequestBody QuestaoRequestDTO questaoRequestDTO) {
        QuestaoResponseDTO questaoResponseDTO = questaoService.cadastrarQuestao(questaoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(questaoResponseDTO);
    }

    @PutMapping("/{idQuestao}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    public ResponseEntity<QuestaoResponseDTO> atualizarQuestao(@PathVariable Long idQuestao, @Valid @RequestBody QuestaoRequestDTO questaoRequestDTO) {
        QuestaoResponseDTO questaoResponseDTO = questaoService.atualizarQuestao(idQuestao, questaoRequestDTO);
        return ResponseEntity.ok(questaoResponseDTO);
    }

    @DeleteMapping("/{idQuestao}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    public ResponseEntity<String> deletarQuestao(@PathVariable Long idQuestao) {
        questaoService.deletarQuestao(idQuestao);
        return ResponseEntity.ok("Questão com ID " + idQuestao + " deletada com sucesso.");
    }
}
