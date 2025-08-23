package br.com.quizmaster.quiz.model;

import br.com.quizmaster.quiz.rest.dto.QuestaoResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Questao")
public class QuestaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idQuestao")
    private Long idQuestao;

    @NotNull(message = "O enunciado não pode ser nulo.")
    @NotBlank(message = "O enunciado é obrigatório.")
    @Column(name = "enunciado", length = 255, nullable = false)
    private String enunciado;

    @NotNull(message = "O tipo de questão não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoQuestao", nullable = false)
    private TipoQuestao tipoQuestao;

    @Column(name = "respostaEsperada", length = 255)
    private String respostaEsperada;

    @ManyToOne
    @JoinColumn(name = "idQuiz", nullable = false)
    @NotNull(message = "A questão deve estar associada a um quiz e não pode ser nula.")
    private QuizModel idQuiz;

    public QuestaoResponseDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, QuestaoResponseDTO.class);
    }
}