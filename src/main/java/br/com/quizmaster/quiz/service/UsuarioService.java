package br.com.quizmaster.quiz.service;

import br.com.quizmaster.quiz.exception.*;
import br.com.quizmaster.quiz.model.UsuarioModel;
import br.com.quizmaster.quiz.repository.UsuarioRepository;
import br.com.quizmaster.quiz.rest.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obterUsuarios() {
        return usuarioRepository
                .findAll()
                .stream()
                .map(UsuarioModel::toDTO)
                .collect(Collectors.toList());
    }



    @Transactional
    public UsuarioDTO cadastrarUsuario(UsuarioModel usuarioModel) {
        try {
            if (usuarioRepository.existsByLoginEmail(usuarioModel.getLoginEmail())) {
                throw new ConstraintException("Já exite um usuário cadastrado com o e-mail '" + usuarioModel.getLoginEmail() + "' na base de dados!");
            }

            return usuarioRepository.save(usuarioModel).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível cadastrar o usuário!");

        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao cadastar o usuário!");
            }

            throw e;

        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível cadastrar o usuário. Violação de regra de negócio!");

        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível cadastrar o usuário. Falha na conexão com o banco de dados!");

        }
    }

    public UsuarioDTO atualizarUsuario(UsuarioModel usuarioModel) {
        try {
            if (!usuarioRepository.existsByLoginEmail((usuarioModel.getLoginEmail()))) {
                throw new ConstraintException("O usuário com o e-mail '" + usuarioModel.getLoginEmail() +"' não existe na base de dados!");
            }

            return usuarioRepository.save(usuarioModel).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar o usuário!");

        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao atualizar o usuário!");
            }

            throw e;

        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar o usuário. Violação de regra de negócio!");

        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o usuário. Falha na conexão com o banco de dados!");

        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar o usuário com o e-mail '" + usuarioModel.getLoginEmail() + "'. Não encontrado no banco de dados!");
        }
    }

    public void deletarUsuario(UsuarioModel usuarioModel) {
        try {
            if (!usuarioRepository.existsByLoginEmail((usuarioModel.getLoginEmail()))) {
                throw new ConstraintException("O usuário com o e-mail '" + usuarioModel.getLoginEmail() +"' não existe na base de dados!");
            }

            usuarioRepository.delete(usuarioModel);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar o usuário!");

        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao deletar o usuário!");
            }

            throw e;

        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o usuário. Violação de regra de negócio!");

        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar o usuário. Falha na conexão com o banco de dados!");

        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar o usuário com o e-mail '" + usuarioModel.getLoginEmail() + "'. Não encontrado no banco de dados!");
        }
    }
}