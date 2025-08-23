package br.com.quizmaster.quiz;

import br.com.quizmaster.quiz.model.TipoUsuario;
import br.com.quizmaster.quiz.model.UsuarioModel;
import br.com.quizmaster.quiz.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class QuizApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);
	}


    @Bean
    public CommandLineRunner commandLineRunner(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Usuário Admin
            if (repository.findByLoginEmail("admin@quizmaster.com").isEmpty()) {
                UsuarioModel admin = new UsuarioModel();
                admin.setNome("Admin");
                admin.setLoginEmail("admin@quizmaster.com");
                admin.setSenha(passwordEncoder.encode("admin123"));
                admin.setTipoUsuario(TipoUsuario.ADMIN);
                repository.save(admin);
                System.out.println(">>> Usuário admin de teste criado!");
            }

            // Usuário Aluno
            if (repository.findByLoginEmail("aluno@quizmaster.com").isEmpty()) {
                UsuarioModel aluno = new UsuarioModel();
                aluno.setNome("Aluno Teste");
                aluno.setLoginEmail("aluno@quizmaster.com");
                aluno.setSenha(passwordEncoder.encode("aluno123"));
                aluno.setTipoUsuario(TipoUsuario.ALUNO);
                repository.save(aluno);
                System.out.println(">>> Usuário aluno de teste criado!");
            }
        };
    }

}
