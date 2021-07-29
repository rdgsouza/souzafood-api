package com.souza.souzafood.api.v1.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UrlFotoProdutoModel {

	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
	private String url;

}
