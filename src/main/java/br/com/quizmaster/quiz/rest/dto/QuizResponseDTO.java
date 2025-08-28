package br.com.quizmaster.quiz.rest.dto;

import br.com.quizmaster.quiz.model.QuizModel;
import br.com.quizmaster.quiz.model.TurmaModel;
import br.com.quizmaster.quiz.model.UsuarioModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
public class QuizResponseDTO {
    private Long idQuiz;
    private String titulo;
    private String descricao; // Adicionei este campo que faltava
    private LocalDate dataCriacao;
    private String categoria;

    // AGORA USAMOS OS DTOs SEGUROS
    private SimpleUsuarioDTO professor;
    private SimpleTurmaDTO turma;

    // O método toModel() geralmente não é necessário em um DTO de RESPOSTA.
    // Ele é mais comum em DTOs de Requisição. Pode ser removido.
}