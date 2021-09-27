package com.souza.souzafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.souza.souzafood.api.exceptionhandler.Problem;
import com.souza.souzafood.api.model.input.CidadeInput;
import com.souza.souzafood.api.v1.model.CidadeModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//https://app.algaworks.com/aulas/2132/desacoplando-anotacoes-do-swagger-dos-controladores
@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	CollectionModel<CidadeModel> listar(); 
	
	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})

	CidadeModel buscar(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true) 
			Long cidadeId);

	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade Cadastrada"),
	})

	CidadeModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
			CidadeInput cidadeInput);
	
	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade Atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})

	CidadeModel atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true) 
        	Long cidadeId, 
		
        	@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
			CidadeInput cidadeInput);
	
	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluída"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})

	void remover(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true)
			Long cidadeId);
}
