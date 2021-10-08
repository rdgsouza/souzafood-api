package com.souza.souzafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.souza.souzafood.api.v1.model.CozinhaModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//https://app.algaworks.com/aulas/2136/corrigindo-documentacao-com-substituicao-de-page
@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi {
	
	private CozinhasEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	@ApiModel("CozinhasEmbeddedModel")
	@Data
	public class CozinhasEmbeddedModelOpenApi {
		
		private List<CozinhaModel> cozinhas;
	}
	
}
