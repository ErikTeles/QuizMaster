package br.com.quizmaster.quiz.rest.dto;

/**
 * DTO para representar uma alternativa de forma segura na resposta da API.
 * Contém apenas os dados que o frontend precisa ver.
 */
public record AlternativaResponseDTO(
        Long idAlternativa,
        String texto
) {}