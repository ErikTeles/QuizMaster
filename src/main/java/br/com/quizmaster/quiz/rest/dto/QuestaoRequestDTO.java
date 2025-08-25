package br.com.quizmaster.quiz.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import br.com.quizmaster.quiz.model.TipoQuestao;

public record QuestaoRequestDTO(
        @NotBlank(message = "O enunciado da questão é obrigatório.")
        String enunciado,

        @NotNull(message = "É preciso informar a qual quiz esta questão pertence.")
        Long quizId,

        @NotNull(message = "É preciso informar o tipo da questão.")
        TipoQuestao tipoQuestao

) {}