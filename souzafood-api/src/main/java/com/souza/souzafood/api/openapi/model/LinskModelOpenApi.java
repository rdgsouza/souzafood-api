package com.souza.souzafood.api.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("Links")
public class LinskModelOpenApi {
//	https://app.algaworks.com/aulas/2194/corrigindo-as-propriedades-de-links-na-documentacao
	private LinkModel rel;
	
	@Setter
	@Getter
	@ApiModel("Link")
	private class LinkModel {
		
		private String href;
		private boolean template;
		
		
	}
	
}
