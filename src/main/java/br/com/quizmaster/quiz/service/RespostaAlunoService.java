package br.com.quizmaster.quiz.service;

import br.com.quizmaster.quiz.model.*;
import br.com.quizmaster.quiz.repository.*;
import br.com.quizmaster.quiz.rest.dto.CorrecaoQuestaoDTO;
import br.com.quizmaster.quiz.rest.dto.RespostaAlunoRequestDTO;
import br.com.quizmaster.quiz.rest.dto.ResultadoQuizResponseDTO;
import br.com.quizmaster.quiz.rest.dto.SubmissaoQuizRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RespostaAlunoService {

    private final RespostaAlunoRepository respostaAlunoRepository;
    private final QuestaoRepository questaoRepository;
    private final AlternativaRepository alternativaRepository;
    private final QuizRepository quizRepository;

    @Transactional
    public ResultadoQuizResponseDTO salvarECorrigirRespostas(SubmissaoQuizRequestDTO submissao, Authentication authentication) {
        UsuarioModel aluno = (UsuarioModel) authentication.getPrincipal();
        QuizModel quiz = quizRepository.findById(submissao.quizId())
                .orElseThrow(() -> new RuntimeException("Quiz não encontrado com o ID: " + submissao.quizId()));

        int acertos = 0;
        List<CorrecaoQuestaoDTO> correcaoDetalhada = new ArrayList<>();

        for (RespostaAlunoRequestDTO respostaDto : submissao.respostas()) {
            QuestaoModel questao = questaoRepository.findById(respostaDto.questaoId())
                    .orElseThrow(() -> new RuntimeException("Questão não encontrada com o ID: " + respostaDto.questaoId()));

            // Salva a resposta do aluno no banco
            RespostaAlunoModel respostaAluno = new RespostaAlunoModel();
            respostaAluno.setAluno(aluno);
            respostaAluno.setQuestao(questao);
            respostaAluno.setDataResposta(LocalDateTime.now());

            boolean acertou = false;
            String suaResposta = "Não respondida";
            String respostaCorreta = "";

            if (questao.getTipoQuestao() == TipoQuestao.MULTIPLA_ESCOLHA && respostaDto.alternativaId() != null) {
                AlternativaModel alternativaEscolhida = alternativaRepository.findById(respostaDto.alternativaId()).orElse(null);
                respostaAluno.setAlternativaEscolhida(alternativaEscolhida);
                suaResposta = alternativaEscolhida != null ? alternativaEscolhida.getTexto() : "Resposta inválida";

                // Lógica de correção para múltipla escolha
                Optional<AlternativaModel> alternativaCorretaOpt = alternativaRepository.findByQuestaoAndEhCorreta(questao, true);
                if(alternativaCorretaOpt.isPresent()){
                    respostaCorreta = alternativaCorretaOpt.get().getTexto();
                    if (alternativaEscolhida != null && alternativaEscolhida.isEhCorreta()) {
                        acertou = true;
                    }
                }
            }
            // Adicione aqui a lógica para outros tipos de questão (DISSERTATIVA, etc.)

            if (acertou) {
                acertos++;
            }

            respostaAlunoRepository.save(respostaAluno);
            correcaoDetalhada.add(new CorrecaoQuestaoDTO(questao.getIdQuestao(), questao.getEnunciado(), acertou, suaResposta, respostaCorreta));
        }

        int totalQuestoes = submissao.respostas().size();
        double pontuacaoPercentual = (totalQuestoes > 0) ? ((double) acertos / totalQuestoes) * 100 : 0;

        return new ResultadoQuizResponseDTO(
                quiz.getIdQuiz(),
                quiz.getTitulo(),
                totalQuestoes,
                acertos,
                pontuacaoPercentual,
                correcaoDetalhada
        );
    }
}