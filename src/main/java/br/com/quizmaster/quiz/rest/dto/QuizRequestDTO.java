package br.com.quizmaster.quiz.rest.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuizRequestDTO(
        @NotBlank(message = "O título é obrigatório.")
        @Size(min = 3, message = "O título deve ter no mínimo 3 caracteres.")
        String titulo,

        @NotBlank(message = "A descrição é obrigatória.")
        String descricao
) {}