package br.com.quizmaster.quiz.repository;

import br.com.quizmaster.quiz.model.AlternativaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlternativaRepository extends JpaRepository<AlternativaModel, Long> {
}
