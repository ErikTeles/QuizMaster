package br.com.quizmaster.quiz.rest.controller;

import br.com.quizmaster.quiz.model.QuestaoModel;
import br.com.quizmaster.quiz.model.QuizModel;
import br.com.quizmaster.quiz.rest.dto.QuestaoResponseDTO;
import br.com.quizmaster.quiz.rest.dto.QuizResponseDTO;
import br.com.quizmaster.quiz.service.QuestaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questao")
public class QuestaoController {
    @Autowired
    private QuestaoService questaoService;

    @GetMapping
    public ResponseEntity<List<QuestaoResponseDTO>> obterQuestoes() {
        List<QuestaoResponseDTO> questaoResponseDTOS = questaoService.obterQuestoes();
        return ResponseEntity.ok(questaoResponseDTOS);
    }

    @PostMapping
    public ResponseEntity<QuestaoResponseDTO> cadastrarQuestao(@Valid @RequestBody QuestaoModel questaoModel) {
        QuestaoResponseDTO questaoResponseDTO = questaoService.cadastrarQuestao(questaoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(questaoResponseDTO);
    }

    @PutMapping("/{idQuestao}")
    public ResponseEntity<QuestaoResponseDTO> atualizarQuestao(@PathVariable Long idQuestao, @Valid @RequestBody QuestaoModel questaoModel) {
        QuestaoResponseDTO questaoResponseDTO = questaoService.atualizarQuestao(idQuestao, questaoModel);
        return ResponseEntity.status(HttpStatus.OK).body(questaoResponseDTO);
    }

    @DeleteMapping("/{idQuestao}")
    public ResponseEntity<String> deletarQuestao(@PathVariable Long idQuestao) {
        questaoService.deletarQuestao(idQuestao);
        return ResponseEntity.ok("Questão com ID " + idQuestao + " deletada com sucesso.");
    }
}
