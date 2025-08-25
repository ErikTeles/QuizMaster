package br.com.quizmaster.quiz.service;

import br.com.quizmaster.quiz.exception.ObjectNotFoundException;
import br.com.quizmaster.quiz.model.TurmaModel;
import br.com.quizmaster.quiz.model.UsuarioModel;
import br.com.quizmaster.quiz.repository.TurmaRepository;
import br.com.quizmaster.quiz.repository.UsuarioRepository;
import br.com.quizmaster.quiz.rest.dto.TurmaRequestDTO;
import br.com.quizmaster.quiz.rest.dto.TurmaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public List<TurmaResponseDTO> obterTurmas() {
        return turmaRepository
                .findAll()
                .stream()
                .map(TurmaModel::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TurmaResponseDTO cadastrarTurma(TurmaRequestDTO turmaRequestDTO, Authentication authentication) {
        // Pega o usuário que está fazendo a requisição a partir do token.
        UsuarioModel organizador = (UsuarioModel) authentication.getPrincipal();

        // Cria a nova entidade TurmaModel.
        TurmaModel novaTurma = new TurmaModel();
        novaTurma.setNome(turmaRequestDTO.nome());
        novaTurma.setIdOrganizador(organizador); // Define o usuário logado como o organizador.

        // Salva no banco de dados.
        TurmaModel turmaSalva = turmaRepository.save(novaTurma);
        return turmaSalva.toDTO();
    }

    @Transactional
    public TurmaResponseDTO atualizarTurma(Long id, TurmaModel turmaModel) {
        if (!turmaRepository.existsById(id)) {
            throw new ObjectNotFoundException("Turma não encontrada com o ID:" + id);
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
