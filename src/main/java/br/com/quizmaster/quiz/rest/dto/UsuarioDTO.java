package br.com.quizmaster.quiz.rest.dto;

import br.com.quizmaster.quiz.model.UsuarioModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class UsuarioDTO {
    private Long idUsuario;
    private String nome;
    private String loginEmail;
    private String senha;
    private String tipoUsuario;

    public UsuarioModel toModel(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, UsuarioModel.class);
    }
}
