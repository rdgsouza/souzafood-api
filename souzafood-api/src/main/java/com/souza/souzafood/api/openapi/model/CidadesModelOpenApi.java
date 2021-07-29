package com.souza.souzafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.souza.souzafood.api.v1.model.CidadeModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CidadesModel")
@Data
public class CidadesModelOpenApi {
//	https://app.algaworks.com/aulas/2195/corrigindo-a-documentacao-dos-endpoints-de-cidades
	private CidadeEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("CidadesEmbeddedModel")
	@Data
	public class CidadeEmbeddedModelOpenApi {
		
		private List<CidadeModel> cidades;
	}
}
