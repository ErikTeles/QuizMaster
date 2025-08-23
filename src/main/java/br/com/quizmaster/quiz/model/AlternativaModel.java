package br.com.quizmaster.quiz.model;

import br.com.quizmaster.quiz.rest.dto.AlternativaResponseDTO;
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
@Table(name = "Alternativa")
public class AlternativaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlternativa")
    private Long idAlternativa;

    @NotNull(message = "O texto da alternativa não pode ser nulo.")
    @NotBlank(message = "O texto da alternativa é obrigatório.")
    @Column(name = "texto", length = 255, nullable = false)
    private String texto;

    @NotNull(message = "A alternativa tem que ser verdadeira ou falsa, não pode ser nula.")
    @Column(name = "ehCorreta", nullable = false)
    private Boolean ehCorreta;

    @ManyToOne
    @JoinColumn(name = "idQuestao", nullable = false)
    @NotNull(message = "A alternativa deve estar associada a uma questão e não pode ser nula.")
    private QuestaoModel idQuestao;

    public AlternativaResponseDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, AlternativaResponseDTO.class);
    }
}
