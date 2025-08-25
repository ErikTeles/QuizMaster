package br.com.quizmaster.quiz.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record TurmaRequestDTO(
        @NotBlank(message = "O nome da turma é obrigatório.")
        String nome
) {}