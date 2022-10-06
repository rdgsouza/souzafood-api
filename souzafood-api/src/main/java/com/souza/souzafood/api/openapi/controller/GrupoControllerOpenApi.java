package com.souza.souzafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.souza.souzafood.api.exceptionhandler.Problem;
import com.souza.souzafood.api.model.input.GrupoInput;
import com.souza.souzafood.api.v1.model.GrupoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Lista os grupos")
	CollectionModel<GrupoModel> listar();
    
    public GrupoModel buscar(
    		@ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId);
    
    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
    public GrupoModel adicionar(
    		@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true)
            GrupoInput grupoInput);
    
    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Grupo atualizado"),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public GrupoModel atualizar(
    		@ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId,
            
            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados", 
    		required = true)
            GrupoInput grupoInput);
    
    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Grupo excluído"),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public void remover(
    		@ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId);
    
}
