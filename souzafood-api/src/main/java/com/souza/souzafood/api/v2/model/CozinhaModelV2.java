package com.souza.souzafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@ApiModel("CozinhaModel")
@Setter
@Getter
public class CozinhaModelV2 extends RepresentationModel<CozinhaModelV2> {

    @ApiModelProperty(example = "1")
    private Long idCozinha;
    
    @ApiModelProperty(example = "Brasileira")
    private String nomeCozinha;
    
}    