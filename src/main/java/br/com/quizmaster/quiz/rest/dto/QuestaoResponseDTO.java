package br.com.quizmaster.quiz.rest.dto;

import br.com.quizmaster.quiz.model.QuestaoModel;
import br.com.quizmaster.quiz.model.QuizModel;
import br.com.quizmaster.quiz.model.TipoQuestao;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class QuestaoResponseDTO {
    private Long idQuestao;
    private String enunciado;
    private TipoQuestao tipoQuestao;
    private String respostaEsperada;
    private QuizModel idQuiz;

    public QuestaoModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, QuestaoModel.class);
    }
}
