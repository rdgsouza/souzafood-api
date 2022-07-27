package com.souza.souzafood.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.souza.souzafood.domain.exception.EntidadeEmUsoException;
import com.souza.souzafood.domain.exception.EstadoNaoEncontradoException;
import com.souza.souzafood.domain.model.Estado;
import com.souza.souzafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String MSG_ESTADO_EM_USO 
	        = "Estado de código %d não pode ser removido pois estar em uso";

	@Autowired
	private EstadoRepository estadoRepository;

//	@Transactional(propagation = Propagation.NOT_SUPPORTED) OBS: Na anotacao @Transactional existem 
//	escopos que podem ser passados para definir a forma como essa transacao ira trabalhar 
//	Fonte: https://app.algaworks.com/forum/topicos/83398/duvida-com-transacao-no-sdj
	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public void excluir(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
			estadoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(estadoId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}

	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId).orElseThrow(
				() -> new EstadoNaoEncontradoException(estadoId));
	}
}
