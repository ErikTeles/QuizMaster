package br.com.quizmaster.quiz.rest.dto;

import br.com.quizmaster.quiz.model.TurmaModel;
import br.com.quizmaster.quiz.model.UsuarioModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
public class TurmaResponseDTO {
    private Long idTurma;
    private String nome;
    private String descricao;
    private UsuarioModel idOrganizador; // Id do usuário que criou a turma "idUsuario"
    private List<UsuarioModel> alunos;

    public TurmaModel toModel(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, TurmaModel.class);
    }
}
