package br.com.quizmaster.quiz.rest.dto;

// Garanta que está usando a palavra-chave "record" e não "class"
public record RespostaAlunoResponseDTO(
        Long idResposta,
        String status
) {}