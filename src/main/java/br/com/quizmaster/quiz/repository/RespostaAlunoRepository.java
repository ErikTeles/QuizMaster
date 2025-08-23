package br.com.quizmaster.quiz.repository;

import br.com.quizmaster.quiz.model.RespostaAlunoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaAlunoRepository extends JpaRepository<RespostaAlunoModel, Long> {
}
