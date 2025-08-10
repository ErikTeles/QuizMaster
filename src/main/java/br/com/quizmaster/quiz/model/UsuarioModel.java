package br.com.quizmaster.quiz.model;

import br.com.quizmaster.quiz.rest.dto.UsuarioDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usuario")
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long idUsuario;

    @NotNull(message = "O nome não pode ser nulo.")
    @NotBlank(message = "O nome é obrigatório.")
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @NotNull(message = "O e-mail não pode ser nulo.")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido. Verifique o valor informado.")
    @Column(name = "loginEmail", length = 255, nullable = false, unique = true)
    private String loginEmail;

    @NotNull(message = "A senha não pode ser nula.")
    @NotBlank(message = "A senha é obrigatória.")
    @Column(name = "senha", length = 255, nullable = false)
    private String senha;

    @NotNull(message = "O tipo de usuário não pode ser nulo.")
    @NotBlank(message = "O tipo de usuário é obrigatório.")
    @Pattern(regexp = "^(Admin|Aluno|Professor)$", message = "O tipo de usuário deve ser 'Admin', 'Aluno' ou 'Professor'.")
    @Column(name = "tipoUsuario", length = 10, nullable = false)
    private String tipoUsuario;

    public UsuarioDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, UsuarioDTO.class);
    }
}
