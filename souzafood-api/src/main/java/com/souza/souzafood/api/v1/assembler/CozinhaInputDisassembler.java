package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.model.input.CozinhaInput;
import com.souza.souzafood.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
		// Mapeamento para um novo tipo: Cozinha
		return modelMapper.map(cozinhaInput, Cozinha.class);
	}

	public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha) {
		// Mapeamento para uma entidade já criada do tipo: Cozinha
		modelMapper.map(cozinhaInput, cozinha);
	}
}
