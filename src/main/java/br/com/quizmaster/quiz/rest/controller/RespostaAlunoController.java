package br.com.quizmaster.quiz.rest.controller;

import br.com.quizmaster.quiz.rest.dto.ResultadoQuizResponseDTO;
import br.com.quizmaster.quiz.rest.dto.SubmissaoQuizRequestDTO;
import br.com.quizmaster.quiz.service.RespostaAlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resposta-aluno")
public class RespostaAlunoController {

    @Autowired
    private RespostaAlunoService respostaAlunoService;

    @PostMapping
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<ResultadoQuizResponseDTO> salvarRespostas(
            @Valid @RequestBody SubmissaoQuizRequestDTO submissao,
            Authentication authentication) {

        ResultadoQuizResponseDTO resultado = respostaAlunoService.salvarECorrigirRespostas(submissao, authentication);
        return ResponseEntity.ok(resultado);
    }
}