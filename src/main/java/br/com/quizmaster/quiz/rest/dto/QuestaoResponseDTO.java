package br.com.quizmaster.quiz.rest.dto;

import br.com.quizmaster.quiz.model.TipoQuestao;
import java.util.List;

public record QuestaoResponseDTO(
        Long idQuestao,
        String enunciado,
        TipoQuestao tipoQuestao,
        List<AlternativaResponseDTO> alternativas // <-- GARANTA QUE ESTE CAMPO EXISTA
) {}