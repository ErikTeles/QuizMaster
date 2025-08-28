package br.com.quizmaster.quiz.model;

import br.com.quizmaster.quiz.rest.dto.RespostaAlunoResponseDTO;
import jakarta.persistence.*;
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
    private Long idResposta;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioModel aluno;

    @ManyToOne
    @JoinColumn(name = "id_questao", nullable = false)
    private QuestaoModel questao;

    @ManyToOne
    @JoinColumn(name = "id_alternativa")
    private AlternativaModel alternativaEscolhida;

    @Column(name = "resposta_texto")
    private String respostaTexto;

    @Column(nullable = false)
    private LocalDateTime dataResposta;

    // ==========================================================
    // O MÉTODO toDTO() AGORA ESTÁ DENTRO DA CLASSE
    // ==========================================================
    public  RespostaAlunoResponseDTO toDTO() {
        // Para a lógica de correção, o service montará um DTO de resultado mais complexo.
        // Este método aqui pode simplesmente retornar um status básico de sucesso.
        return new RespostaAlunoResponseDTO(this.idResposta, "Salvo com sucesso");
    }
}