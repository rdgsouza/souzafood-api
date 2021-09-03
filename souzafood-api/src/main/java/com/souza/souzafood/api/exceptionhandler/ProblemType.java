package com.souza.souzafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
// https://app.algaworks.com/aulas/1937/padronizando-o-formato-de-problemas-no-corpo-de-respostas-com-a-rfc-7807
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel","Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso","Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	PARAMETRO_INVALIDO("/parametro-invalido","Parâmetro inválido"),
	ERRO_DE_SISTEMA("/erro-de-sistema","Erro de sistema"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"), 
	TAMANHO_MAX_DO_ARQUIVO_EXCEDIDO("/tamanho-maximo-do-arquivo-excedido", "Tamanho máximo do arquivo excedido"),
	ACESSO_NEGADO("/acesso-negado", "Acesso negado"); //ACESSO_NEGADO("/acesso-negado", "Acesso negado"),

	private String uri;
	private String title;
	
	ProblemType(String path, String title) {
		this.uri = "https://souzafood.com.br" + path;
		this.title = title;
	}
}
