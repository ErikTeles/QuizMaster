package br.com.quizmaster.quiz.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SubmissaoQuizRequestDTO(
        @NotNull Long quizId,
        @NotEmpty List<RespostaAlunoRequestDTO> respostas
) {}