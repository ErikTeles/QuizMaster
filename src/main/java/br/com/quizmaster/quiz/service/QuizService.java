package br.com.quizmaster.quiz.service;

import br.com.quizmaster.quiz.exception.ObjectNotFoundException;
import br.com.quizmaster.quiz.model.QuizModel;
import br.com.quizmaster.quiz.repository.QuizRepository;
import br.com.quizmaster.quiz.repository.UsuarioRepository;
import br.com.quizmaster.quiz.rest.dto.QuizResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<QuizResponseDTO> obterQuizzes() {
        return quizRepository
                .findAll()
                .stream()
                .map(QuizModel::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuizResponseDTO cadastrarQuiz(QuizModel quizModel) {
        if (!usuarioRepository.existsById(quizModel.getIdUsuario().getIdUsuario())) {
            throw new ObjectNotFoundException("Usuário criador do quiz não encontrado com o ID:" + quizModel.getIdUsuario().getIdUsuario());
        }

        return quizRepository.save(quizModel).toDTO();
    }

    @Transactional
    public QuizResponseDTO atualizarQuiz(Long id, QuizModel quizModel) {
        if (!quizRepository.existsById(id)) {
            throw new ObjectNotFoundException("Quiz não encontrado com o ID:" + id);
        }

        return quizRepository.save(quizModel).toDTO();
    }

    @Transactional
    public void deletarQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new ObjectNotFoundException("Não foi possível deletar. Quiz não encontrado com o ID: " + id);
        }

        quizRepository.deleteById(id);
    }
}
