package br.com.quizmaster.quiz.service;

import br.com.quizmaster.quiz.exception.ObjectNotFoundException;
import br.com.quizmaster.quiz.model.TurmaModel;
import br.com.quizmaster.quiz.repository.TurmaRepository;
import br.com.quizmaster.quiz.repository.UsuarioRepository;
import br.com.quizmaster.quiz.rest.dto.TurmaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurmaService {
    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<TurmaDTO> obterTurmas() {
        return turmaRepository
                .findAll()
                .stream()
                .map(TurmaModel::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TurmaDTO cadastrarTurma(TurmaModel turmaModel) {
        if (!usuarioRepository.existsById(turmaModel.getIdOrganizador().getIdUsuario())) {
            throw new ObjectNotFoundException("Usuário organizador não encontrado com o ID:" + turmaModel.getIdOrganizador().getIdUsuario());
        }

        return turmaRepository.save(turmaModel).toDTO();
    }

    @Transactional
    public TurmaDTO atualizarTurma(Long id, TurmaModel turmaModel) {
        if (!turmaRepository.existsById(id)) {
            throw new ObjectNotFoundException("Turma não encontrada com o ID:" + turmaModel.getIdTurma());
        }

        return turmaRepository.save(turmaModel).toDTO();
    }

    @Transactional
    public void deletarTurma(Long id) {
        if (!turmaRepository.existsById(id)) {
            throw new ObjectNotFoundException("Não foi possível deletar. Turma não encontrada com o ID: " + id);
        }

        turmaRepository.deleteById(id);
    }
}
