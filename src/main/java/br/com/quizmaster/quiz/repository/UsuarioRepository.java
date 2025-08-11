package br.com.quizmaster.quiz.repository;

import br.com.quizmaster.quiz.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    boolean existsByLoginEmail(String loginEmail);

    Optional<UsuarioModel> findByLoginEmail(String loginEmail);
}
