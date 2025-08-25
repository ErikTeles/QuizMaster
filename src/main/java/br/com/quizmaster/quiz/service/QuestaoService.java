package br.com.quizmaster.quiz.service;

import br.com.quizmaster.quiz.model.QuestaoModel;
import br.com.quizmaster.quiz.model.QuizModel;
import br.com.quizmaster.quiz.repository.QuestaoRepository;
import br.com.quizmaster.quiz.repository.QuizRepository;
import br.com.quizmaster.quiz.rest.dto.QuestaoRequestDTO;
import br.com.quizmaster.quiz.rest.dto.QuestaoResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestaoService {

    private final QuestaoRepository questaoRepository;
    private final QuizRepository quizRepository; // Precisamos dele para encontrar o quiz pai

    @Transactional(readOnly = true)
    public List<QuestaoResponseDTO> buscarQuestoesPorQuiz(Long quizId) {
        List<QuestaoModel> questoes = questaoRepository.findByIdQuiz_IdQuiz(quizId);
        return questoes.stream()
                .map(QuestaoModel::toDTO) // Supondo que você tenha um toDTO em QuestaoModel
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestaoResponseDTO cadastrarQuestao(QuestaoRequestDTO questaoRequestDTO) {
        // 1. Busca o Quiz pai
        QuizModel quizPai = quizRepository.findById(questaoRequestDTO.quizId())
                .orElseThrow(() -> new EntityNotFoundException("Quiz não encontrado com o ID: " + questaoRequestDTO.quizId()));

        // 2. Cria a nova QuestaoModel
        QuestaoModel novaQuestao = new QuestaoModel();
        novaQuestao.setEnunciado(questaoRequestDTO.enunciado());
        novaQuestao.setTipoQuestao(questaoRequestDTO.tipoQuestao()); // Define o tipo

        // 3. Associa a questão ao quiz usando o nome correto do método
        novaQuestao.setIdQuiz(quizPai);

        // 4. Salva a nova questão
        QuestaoModel questaoSalva = questaoRepository.save(novaQuestao);
        return questaoSalva.toDTO();
    }

    @Transactional
    public QuestaoResponseDTO atualizarQuestao(Long idQuestao, QuestaoRequestDTO questaoRequestDTO) {
        // 1. Busca a questão existente pelo ID.
        QuestaoModel questaoExistente = questaoRepository.findById(idQuestao)
                .orElseThrow(() -> new EntityNotFoundException("Questão não encontrada com o ID: " + idQuestao));

        // 2. Atualiza os campos (neste caso, apenas o enunciado).
        questaoExistente.setEnunciado(questaoRequestDTO.enunciado());

        // 3. Salva a entidade atualizada.
        QuestaoModel questaoAtualizada = questaoRepository.save(questaoExistente);
        return questaoAtualizada.toDTO();
    }

    @Transactional
    public void deletarQuestao(Long idQuestao) {
        if (!questaoRepository.existsById(idQuestao)) {
            throw new EntityNotFoundException("Não foi possível deletar. Questão não encontrada com o ID: " + idQuestao);
        }
        questaoRepository.deleteById(idQuestao);
    }
}