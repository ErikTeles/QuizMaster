package br.com.quizmaster.quiz.repository;

import br.com.quizmaster.quiz.model.AlternativaModel;
import br.com.quizmaster.quiz.model.QuestaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlternativaRepository extends JpaRepository<AlternativaModel, Long> {

    // ADICIONE ESTE MÉTODO:
    // Ele encontrará a alternativa correta (ehCorreta = true) para uma dada questão.
    Optional<AlternativaModel> findByQuestaoAndEhCorreta(QuestaoModel questao, boolean ehCorreta);

}