package br.com.quizmaster.quiz.model;

import br.com.quizmaster.quiz.rest.dto.AlternativaResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // <-- ADICIONE ESTA ANOTAÇÃO (ou @Getter/@Setter)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Alternativa")
public class AlternativaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlternativa;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private boolean ehCorreta; // O @Data vai criar o método isEhCorreta() para este campo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_questao", nullable = false)
    private QuestaoModel questao;

        public AlternativaResponseDTO toDTO() {
        return new AlternativaResponseDTO(this.idAlternativa, this.texto);
    }
}