package br.com.quizmaster.quiz.model;

import br.com.quizmaster.quiz.rest.dto.TurmaDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Turma")
public class TurmaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTurma")
    private Long idTurma;

    @NotNull(message = "O nome não pode ser nulo.")
    @NotBlank(message = "O nome é obrigatório.")
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @NotNull(message = "O organizador da turma não pode ser nulo.")
    private UsuarioModel idOrganizador; // Id do usuário que criou a turma "idUsuario"

    @ManyToMany
    @JoinTable(
            name = "AlunoTurma",
            joinColumns = @JoinColumn(name = "idTurma"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario")
    )
    @SQLRestriction("tipoUsuario = 'ROLE_ALUNO'")
    private List<UsuarioModel> alunos;

    public TurmaDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, TurmaDTO.class);
    }
}
