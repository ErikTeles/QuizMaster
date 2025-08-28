package br.com.quizmaster.quiz.rest.dto;

import java.util.List;

public record ResultadoQuizResponseDTO(
        Long quizId,
        String tituloQuiz,
        int totalQuestoes,
        int acertos,
        double pontuacaoPercentual, // Ex: 80.0 para 80%
        List<CorrecaoQuestaoDTO> correcaoDetalhada
) {}