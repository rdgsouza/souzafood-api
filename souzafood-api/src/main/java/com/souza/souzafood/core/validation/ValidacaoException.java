package com.souza.souzafood.core.validation;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
//	https://app.algaworks.com/aulas/1967/executando-processo-de-validacao-programaticamente
	
	private BindingResult bindingResult;
	
	
}
