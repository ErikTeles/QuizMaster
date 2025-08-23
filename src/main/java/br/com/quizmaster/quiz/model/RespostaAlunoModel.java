package br.com.quizmaster.quiz.model;

import br.com.quizmaster.quiz.rest.dto.RespostaAlunoResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RespostaAluno")
public class RespostaAlunoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResposta")
    private Long idResposta;

    @NotNull(message = "A data de resposta não pode ser nula.")
    @Column(name = "dataResposta", nullable = false)
    private LocalDateTime dataResposta;

    @Column(name = "respostaTexto", length = 255)
    private String respostaTexto;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @NotNull(message = "O usuário da resposta não pode ser nulo.")
    private UsuarioModel idUsuario;

    @ManyToOne
    @JoinColumn(name = "idQuestao", nullable = false)
    @NotNull(message = "A questão respondida pelo usuário não pode ser nula.")
    private QuestaoModel idQuestao;

    @ManyToOne
    @JoinColumn(name = "idAlternativa")
    private AlternativaModel idAlternativa; //A resposta não necessariamente precisa ter uma alternativa, pode ser uma questão discurssiva

    public RespostaAlunoResponseDTO toDTO(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, RespostaAlunoResponseDTO.class);
    }
}
