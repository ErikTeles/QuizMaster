package br.com.quizmaster.quiz.service;

import br.com.quizmaster.quiz.exception.ObjectNotFoundException;
import br.com.quizmaster.quiz.model.QuizModel;
import br.com.quizmaster.quiz.model.RespostaAlunoModel;
import br.com.quizmaster.quiz.repository.QuestaoRepository;
import br.com.quizmaster.quiz.repository.RespostaAlunoRepository;
import br.com.quizmaster.quiz.repository.UsuarioRepository;
import br.com.quizmaster.quiz.rest.dto.QuizResponseDTO;
import br.com.quizmaster.quiz.rest.dto.RespostaAlunoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespostaAlunoService {
    @Autowired
    private RespostaAlunoRepository respostaAlunoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private QuestaoRepository questaoRepository;

    @Transactional(readOnly = true)
    public List<RespostaAlunoResponseDTO> obterRespostaAlunos() {
        return respostaAlunoRepository
                .findAll()
                .stream()
                .map(RespostaAlunoModel::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RespostaAlunoResponseDTO enviarResposta(RespostaAlunoModel respostaAlunoModel) {
        if (!usuarioRepository.existsById(respostaAlunoModel.getIdUsuario().getIdUsuario())) {
            throw new ObjectNotFoundException("Usuário da resposta não encontrado com o ID:" + respostaAlunoModel.getIdUsuario().getIdUsuario());
        }

        if (!questaoRepository.existsById(respostaAlunoModel.getIdQuestao().getIdQuestao())) {
            throw new ObjectNotFoundException("Questão da resposta não encontrada com o ID:" + respostaAlunoModel.getIdQuestao().getIdQuestao());
        }

        return respostaAlunoRepository.save(respostaAlunoModel).toDTO();
    }

    @Transactional
    public RespostaAlunoResponseDTO atualizarResposta(Long id, RespostaAlunoModel respostaAlunoModel) {
        if (!respostaAlunoRepository.existsById(id)) {
            throw new ObjectNotFoundException("Resposta não encontrada com o ID:" + id);
        }

        return respostaAlunoRepository.save(respostaAlunoModel).toDTO();
    }

    @Transactional
    public void deletarResposta(Long id) {
        if (!respostaAlunoRepository.existsById(id)) {
            throw new ObjectNotFoundException("Não foi possível deletar. Resposta não encontrada com o ID: " + id);
        }

        respostaAlunoRepository.deleteById(id);
    }
}
