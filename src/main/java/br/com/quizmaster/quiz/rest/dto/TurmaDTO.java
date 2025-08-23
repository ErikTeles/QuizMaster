package br.com.quizmaster.quiz.rest.dto;

import br.com.quizmaster.quiz.model.TurmaModel;
import br.com.quizmaster.quiz.model.UsuarioModel;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
public class TurmaDTO {
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
