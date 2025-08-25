package br.com.quizmaster.quiz.service;

import br.com.quizmaster.quiz.exception.ObjectNotFoundException;
import br.com.quizmaster.quiz.model.QuizModel;
import br.com.quizmaster.quiz.model.UsuarioModel;
import br.com.quizmaster.quiz.repository.QuizRepository;
import br.com.quizmaster.quiz.repository.UsuarioRepository;
import br.com.quizmaster.quiz.rest.dto.QuizRequestDTO;
import br.com.quizmaster.quiz.rest.dto.QuizResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
    public QuizResponseDTO cadastrarQuiz(QuizRequestDTO quizRequestDTO, Authentication authentication) {
        UsuarioModel criador = (UsuarioModel) authentication.getPrincipal();

        // 2. Cria uma nova entidade QuizModel.
        QuizModel novoQuiz = new QuizModel();

        // 3. Preenche os dados do novo quiz com as informações do DTO e do usuário logado.
        novoQuiz.setTitulo(quizRequestDTO.titulo());
        novoQuiz.setDescricao(quizRequestDTO.descricao());
        novoQuiz.setIdUsuario(criador);
        novoQuiz.setDataCriacao(LocalDate.now());

        // 4. Salva a nova entidade no banco de dados.
        QuizModel quizSalvo = quizRepository.save(novoQuiz);
        return quizSalvo.toDTO();
    }

    @Transactional
    public QuizResponseDTO atualizarQuiz(Long idQuiz, QuizRequestDTO quizRequestDTO) {
        // 1. Busca o quiz que já existe no banco pelo ID (Padrão "Buscar").
        QuizModel quizExistente = quizRepository.findById(idQuiz)
                .orElseThrow(() -> new EntityNotFoundException("Quiz não encontrado com o ID: " + idQuiz));

        // 2. Atualiza os campos do quiz existente com os novos dados do DTO (Padrão "Atualizar").
        quizExistente.setTitulo(quizRequestDTO.titulo());
        quizExistente.setDescricao(quizRequestDTO.descricao());

        // 3. Salva a entidade atualizada (Padrão "Salvar"). O JPA entende que é um UPDATE.
        QuizModel quizAtualizado = quizRepository.save(quizExistente);
        return quizAtualizado.toDTO();
    }

    @Transactional
    public void deletarQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new ObjectNotFoundException("Não foi possível deletar. Quiz não encontrado com o ID: " + id);
        }

        quizRepository.deleteById(id);
    }
}
