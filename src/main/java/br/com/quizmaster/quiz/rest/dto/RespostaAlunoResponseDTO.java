package br.com.quizmaster.quiz.rest.dto;

import br.com.quizmaster.quiz.model.AlternativaModel;
import br.com.quizmaster.quiz.model.QuestaoModel;
import br.com.quizmaster.quiz.model.RespostaAlunoModel;
import br.com.quizmaster.quiz.model.UsuarioModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
public class RespostaAlunoResponseDTO {
    private Long idResposta;
    private LocalDateTime dataResposta;
    private String respostaTexto;
    private UsuarioModel idUsuario;
    private QuestaoModel idQuestao;
    private AlternativaModel idAlternativa; //A resposta não necessariamente precisa ter uma alternativa, pode ser uma questão discurssiva

    public RespostaAlunoModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, RespostaAlunoModel.class);
    }
}
