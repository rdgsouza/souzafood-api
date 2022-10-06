package com.souza.souzafood.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EnvioEmailService {

	void enviar(Mensagem mensagem);

	@Getter
	@Builder
	class Mensagem {
		
		@Singular 
		private Set<String> destinatarios; 
		
		@NonNull
		private String assunto;
		
		@NonNull
		private String corpo;
		
//		Aula: https://www.algaworks.com/aulas/2084/processando-template-do-corpo-de-e-mails-com-apache-freemarker
		@Singular("variavel") // A anotação @Singular é usada junto com a anotação do Lombok @Builder. 
//		Se você anotar um dos campos com valor de coleção com a anotação @Singular o Lombok gera para 
//		o campo: Um método 'variavel' que foi especificado acima para adicionar um único elemento à coleção.
		private Map<String, Object> variaveis;
		
	}
}
