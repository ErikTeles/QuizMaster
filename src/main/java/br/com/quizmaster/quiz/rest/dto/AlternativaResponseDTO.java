package br.com.quizmaster.quiz.rest.dto;

import br.com.quizmaster.quiz.model.AlternativaModel;
import br.com.quizmaster.quiz.model.QuestaoModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class AlternativaResponseDTO {
    private Long idAlternativa;
    private String texto;
    private Boolean ehCorreta;
    private QuestaoModel idQuestao;

    public AlternativaModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, AlternativaModel.class);
    }
}
