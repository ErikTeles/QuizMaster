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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usuario")
public class UsuarioModel implements UserDetails {
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
    @Enumerated(EnumType.STRING) // Diz ao JPA para salvar o nome do enum (ex: "ROLE_ADMIN") como texto
    @Column(name = "tipoUsuario", nullable = false) // Mantemos o nome da coluna original
    private Role role;

    public UsuarioDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(UsuarioModel.class, UsuarioDTO.class).addMappings(mapper -> {
            mapper.map(UsuarioModel::getRole, UsuarioDTO::setTipoUsuario);
        });
        return modelMapper.map(this, UsuarioDTO.class);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna a lista de papéis (autorizações) do usuário
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        // Retorna a senha do usuário
        return this.senha;
    }

    @Override
    public String getUsername() {
        // Retorna o identificador de login do usuário (no nosso caso, o email)
        return this.loginEmail;
    }

    // Os métodos abaixo controlam regras de expiração de conta/senha.
    // Para simplificar, vamos retornar 'true' para todos.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
