package br.com.quizmaster.quiz.rest.controller;

import br.com.quizmaster.quiz.model.AlternativaModel;
import br.com.quizmaster.quiz.model.QuestaoModel;
import br.com.quizmaster.quiz.rest.dto.AlternativaResponseDTO;
import br.com.quizmaster.quiz.rest.dto.QuestaoResponseDTO;
import br.com.quizmaster.quiz.service.AlternativaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alternativa")
public class AlternativaController {
    private AlternativaService alternativaService;

    @GetMapping
    public ResponseEntity<List<AlternativaResponseDTO>> obterAlternativas() {
        List<AlternativaResponseDTO> alternativaResponseDTOS = alternativaService.obterAlternativas();
        return ResponseEntity.ok(alternativaResponseDTOS);
    }

    @PostMapping
    public ResponseEntity<AlternativaResponseDTO> cadastrarAlternativa(@Valid @RequestBody AlternativaModel alternativaModel) {
        AlternativaResponseDTO alternativaResponseDTO = alternativaService.cadastrarAlternativa(alternativaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(alternativaResponseDTO);
    }

    @PutMapping("/{idAlternativa}")
    public ResponseEntity<AlternativaResponseDTO> atualizarAlternativa(@PathVariable Long idAlternativa, @Valid @RequestBody AlternativaModel alternativaModel) {
        AlternativaResponseDTO alternativaResponseDTO = alternativaService.atualizarAlternativa(idAlternativa, alternativaModel);
        return ResponseEntity.status(HttpStatus.OK).body(alternativaResponseDTO);
    }

    @DeleteMapping("/{idAlternativa}")
    public ResponseEntity<String> deletarAlternativa(@PathVariable Long idAlternativa) {
        alternativaService.deletarAlternativa(idAlternativa);
        return ResponseEntity.ok("Alternativa com ID " + idAlternativa + " deletada com sucesso.");
    }
}
