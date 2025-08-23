package br.com.quizmaster.quiz.rest.controller;

import br.com.quizmaster.quiz.model.QuizModel;
import br.com.quizmaster.quiz.model.RespostaAlunoModel;
import br.com.quizmaster.quiz.rest.dto.QuizResponseDTO;
import br.com.quizmaster.quiz.rest.dto.RespostaAlunoResponseDTO;
import br.com.quizmaster.quiz.service.RespostaAlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resposta")
public class RespostaAlunoController {
    @Autowired
    private RespostaAlunoService respostaAlunoService;

    @GetMapping
    public ResponseEntity<List<RespostaAlunoResponseDTO>> obterRespostaAlunos() {
        List<RespostaAlunoResponseDTO> respostaAlunoResponseDTOS = respostaAlunoService.obterRespostaAlunos();
        return ResponseEntity.ok(respostaAlunoResponseDTOS);
    }

    @PostMapping
    public ResponseEntity<RespostaAlunoResponseDTO> enviarResposta(@Valid @RequestBody RespostaAlunoModel respostaAlunoModel) {
        RespostaAlunoResponseDTO respostaAlunoResponseDTO = respostaAlunoService.enviarResposta(respostaAlunoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(respostaAlunoResponseDTO);
    }

    @PutMapping("/{idResposta}")
    public ResponseEntity<RespostaAlunoResponseDTO> atualizarResposta(@PathVariable Long idResposta, @Valid @RequestBody RespostaAlunoModel respostaAlunoModel) {
        RespostaAlunoResponseDTO respostaAlunoResponseDTO = respostaAlunoService.atualizarResposta(idResposta, respostaAlunoModel);
        return ResponseEntity.status(HttpStatus.OK).body(respostaAlunoResponseDTO);
    }

    @DeleteMapping("/{idResposta}")
    public ResponseEntity<String> deletarResposta(@PathVariable Long idResposta) {
        respostaAlunoService.deletarResposta(idResposta);
        return ResponseEntity.ok("Resposta com ID " + idResposta + " deletada com sucesso.");
    }
}
