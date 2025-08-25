package br.com.quizmaster.quiz.service;

import br.com.quizmaster.quiz.exception.*;
import br.com.quizmaster.quiz.model.TipoUsuario;
import br.com.quizmaster.quiz.model.UsuarioModel;
import br.com.quizmaster.quiz.repository.UsuarioRepository;
import br.com.quizmaster.quiz.rest.dto.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> obterUsuarios() {
        return usuarioRepository
                .findAll()
                .stream()
                .map(UsuarioModel::toDTO)
                .collect(Collectors.toList());
    }



    @Transactional
    public UsuarioResponseDTO cadastrarUsuario(UsuarioModel usuarioModel) {
        // 1. VERIFICA SE O E-MAIL JÁ EXISTE
        if (usuarioRepository.findByLoginEmail(usuarioModel.getLoginEmail()).isPresent()) {
            throw new ConstraintException("Já existe um usuário cadastrado com o e-mail '" + usuarioModel.getLoginEmail() + "'!");
        }

        // 2. CRIPTOGRAFA A SENHA (PASSO DE SEGURANÇA ESSENCIAL)
        String senhaCriptografada = passwordEncoder.encode(usuarioModel.getPassword());
        usuarioModel.setSenha(senhaCriptografada);

        // 3. DEFINE UMA ROLE PADRÃO SE NECESSÁRIO
        if (usuarioModel.getTipoUsuario() == null) {
            usuarioModel.setTipoUsuario(TipoUsuario.ROLE_ALUNO);
        }

        // 4. SALVA O USUÁRIO NO BANCO
        UsuarioModel novoUsuario = usuarioRepository.save(usuarioModel);
        return novoUsuario.toDTO();
    }

    @Transactional
    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioModel dadosAtualizados) {
        // 1. BUSCA O USUÁRIO PELO ID FORNECIDO NA URL
        UsuarioModel usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado com o ID: " + id));

        // 2. ATUALIZA OS DADOS DO USUÁRIO ENCONTRADO
        usuarioExistente.setNome(dadosAtualizados.getNome());
        usuarioExistente.setLoginEmail(dadosAtualizados.getLoginEmail());

        // 3. ATUALIZA A SENHA APENAS SE UMA NOVA FOR FORNECIDA
        if (dadosAtualizados.getPassword() != null && !dadosAtualizados.getPassword().isBlank()) {
            String novaSenhaCriptografada = passwordEncoder.encode(dadosAtualizados.getPassword());
            usuarioExistente.setSenha(novaSenhaCriptografada);
        }

        // 4. SALVA AS ALTERAÇÕES
        UsuarioModel usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return usuarioAtualizado.toDTO();
    }

    @Transactional
    public void deletarUsuario(Long id) {
        // 1. VERIFICA SE O USUÁRIO EXISTE ANTES DE DELETAR
        if (!usuarioRepository.existsById(id)) {
            throw new ObjectNotFoundException("Não foi possível deletar. Usuário não encontrado com o ID: " + id);
        }

        // 2. DELETA O USUÁRIO PELO ID
        usuarioRepository.deleteById(id);
    }
}
