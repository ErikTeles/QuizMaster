package br.com.quizmaster.quiz.repository;

import br.com.quizmaster.quiz.model.QuestaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestaoRepository extends JpaRepository<QuestaoModel, Long> {

    List<QuestaoModel> findByIdQuiz_IdQuiz(Long quizId);
}