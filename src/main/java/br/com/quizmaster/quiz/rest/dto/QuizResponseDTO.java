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
    private LocalDate dataCriacao;
    private String categoria;
    private UsuarioModel idUsuario;
    private TurmaModel idTurma;

    public QuizModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, QuizModel.class);
    }
}