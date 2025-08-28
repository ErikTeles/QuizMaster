package br.com.quizmaster.quiz.rest.dto;

public record CorrecaoQuestaoDTO(
        Long questaoId,
        String enunciado,
        boolean acertou,
        String suaResposta,
        String respostaCorreta // Para mostrar ao aluno a resposta certa
) {}
