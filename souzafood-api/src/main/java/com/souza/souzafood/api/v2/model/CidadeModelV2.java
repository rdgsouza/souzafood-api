package com.souza.souzafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Relation(collectionRelation = "cidades" )
@ApiModel("CidadeModel")
@Getter
@Setter
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {
	
//	@ApiModelProperty(value = "ID da cidade", example = "1")
	@ApiModelProperty(example = "1")
	private Long idCidade;
	@ApiModelProperty(example = "Uberlândia")
	private String nomeCidade;
	
	private Long idEstado;
	private String nomeEstado;

}