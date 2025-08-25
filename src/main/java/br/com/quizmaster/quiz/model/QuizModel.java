package br.com.quizmaster.quiz.model;

import br.com.quizmaster.quiz.rest.dto.QuizResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Quiz")
public class QuizModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idQuiz")
    private Long idQuiz;

    @NotNull(message = "O título não pode ser nulo.")
    @NotBlank(message = "O título é obrigatório.")
    @Column(name = "titulo", length = 255, nullable = false)
    private String titulo;

    @NotNull(message = "A data de criação não pode ser nula.")
    @Column(name = "dataCriacao", nullable = false)
    private LocalDate dataCriacao;

    @Column(name = "categoria", length = 255)
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @NotNull(message = "O criador do quiz não pode ser nulo.")
    private UsuarioModel idUsuario;

    @ManyToOne
    @JoinColumn(name = "idTurma")
    private TurmaModel idTurma;

    @Column(name = "descricao", columnDefinition = "TEXT") // Usar TEXT é bom para descrições que podem ser longas
    private String descricao;

    public QuizResponseDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, QuizResponseDTO.class);
    }
}
