package br.com.quizmaster.quiz.service;

import br.com.quizmaster.quiz.exception.ObjectNotFoundException;
import br.com.quizmaster.quiz.model.QuestaoModel;
import br.com.quizmaster.quiz.model.QuizModel;
import br.com.quizmaster.quiz.repository.QuestaoRepository;
import br.com.quizmaster.quiz.repository.QuizRepository;
import br.com.quizmaster.quiz.rest.dto.QuestaoResponseDTO;
import br.com.quizmaster.quiz.rest.dto.QuizResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestaoService {
    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Transactional(readOnly = true)
    public List<QuestaoResponseDTO> obterQuestoes() {
        return questaoRepository
                .findAll()
                .stream()
                .map(QuestaoModel::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestaoResponseDTO cadastrarQuestao(QuestaoModel questaoModel) {
        if (!quizRepository.existsById(questaoModel.getIdQuiz().getIdQuiz())) {
            throw new ObjectNotFoundException("Quiz não encontrado com o ID:" + questaoModel.getIdQuiz().getIdQuiz());
        }

        return questaoRepository.save(questaoModel).toDTO();
    }

    @Transactional
    public QuestaoResponseDTO atualizarQuestao(Long id, QuestaoModel questaoModel) {
        if (!questaoRepository.existsById(id)) {
            throw new ObjectNotFoundException("Questão não encontrada com o ID:" + id);
        }

        return questaoRepository.save(questaoModel).toDTO();
    }

    @Transactional
    public void deletarQuestao(Long id) {
        if (!questaoRepository.existsById(id)) {
            throw new ObjectNotFoundException("Não foi possível deletar. Questão não encontrada com o ID: " + id);
        }

        questaoRepository.deleteById(id);
    }
}
