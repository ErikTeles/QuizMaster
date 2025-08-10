package br.com.quizmaster.quiz.repository;

import br.com.quizmaster.quiz.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    boolean existsByLoginEmail(String loginEmail);
}