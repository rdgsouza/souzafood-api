package com.souza.souzafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.souza.souzafood.domain.exception.NegocioException;
import com.souza.souzafood.domain.exception.UsuarioNaoEncontradoException;
import com.souza.souzafood.domain.model.Grupo;
import com.souza.souzafood.domain.model.Usuario;
import com.souza.souzafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
	private CadastroGrupoService cadastroGrupo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;   
    
    @Transactional
    public Usuario salvar(Usuario usuario) {

    	usuarioRepository.detach(usuario); // Método criado para resolver o erro de sincronização. Aula: https://www.algaworks.com/aulas/2017/implementando-regra-de-negocio-para-evitar-usuarios-com-e-mails-duplicados
    	
    	Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
    	
    	if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
    		throw new NegocioException(String.format(
    				"Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
    	}
    	
    	if (usuario.isNovo()) {
    		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    	}
    	
        return usuarioRepository.save(usuario);
    }
    
    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        
        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        
        usuario.setSenha(passwordEncoder.encode(novaSenha));
    }
    
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

		usuario.removerGrupo(grupo);
	}

	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

		usuario.adicionarGrupo(grupo);
	}
    

    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }            
}    