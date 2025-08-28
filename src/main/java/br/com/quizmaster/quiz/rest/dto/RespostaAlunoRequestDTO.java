package br.com.quizmaster.quiz.rest.dto;

import jakarta.validation.constraints.NotNull;

public record RespostaAlunoRequestDTO(
        @NotNull Long questaoId,
        Long alternativaId, // Opcional, para múltipla escolha
        String respostaTexto // Opcional, para dissertativas
) {}