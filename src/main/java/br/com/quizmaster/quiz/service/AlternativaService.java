package br.com.quizmaster.quiz.service;

import br.com.quizmaster.quiz.exception.ObjectNotFoundException;
import br.com.quizmaster.quiz.model.AlternativaModel;
import br.com.quizmaster.quiz.model.QuestaoModel;
import br.com.quizmaster.quiz.repository.AlternativaRepository;
import br.com.quizmaster.quiz.repository.QuestaoRepository;
import br.com.quizmaster.quiz.rest.dto.AlternativaResponseDTO;
import br.com.quizmaster.quiz.rest.dto.QuestaoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlternativaService {
    @Autowired
    private AlternativaRepository alternativaRepository;

    @Autowired
    private QuestaoRepository questaoRepository;

    @Transactional(readOnly = true)
    public List<AlternativaResponseDTO> obterAlternativas() {
        return alternativaRepository
                .findAll()
                .stream()
                .map(AlternativaModel::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AlternativaResponseDTO cadastrarAlternativa(AlternativaModel alternativaModel) {
        if (!questaoRepository.existsById(alternativaModel.getQuestao().getIdQuestao())) {
            throw new ObjectNotFoundException("Questão não encontrada com o ID:" + alternativaModel.getQuestao().getIdQuestao() );
        }

        return alternativaRepository.save(alternativaModel).toDTO();
    }

    @Transactional
    public AlternativaResponseDTO atualizarAlternativa(Long id, AlternativaModel alternativaModel) {
        if (!alternativaRepository.existsById(id)) {
            throw new ObjectNotFoundException("Alternativa não encontrada com o ID:" + id);
        }

        return alternativaRepository.save(alternativaModel).toDTO();
    }

    @Transactional
    public void deletarAlternativa(Long id) {
        if (!alternativaRepository.existsById(id)) {
            throw new ObjectNotFoundException("Não foi possível deletar. Alternativa não encontrada com o ID: " + id);
        }

        alternativaRepository.deleteById(id);
    }
}
