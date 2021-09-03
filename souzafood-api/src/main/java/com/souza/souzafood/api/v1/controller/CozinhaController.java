package com.souza.souzafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.souza.souzafood.api.model.input.CozinhaInput;
import com.souza.souzafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.souza.souzafood.api.v1.assembler.CozinhaInputDisassembler;
import com.souza.souzafood.api.v1.assembler.CozinhaModelAssembler;
import com.souza.souzafood.api.v1.model.CozinhaModel;
import com.souza.souzafood.core.security.CheckSecurity;
import com.souza.souzafood.domain.model.Cozinha;
import com.souza.souzafood.domain.repository.CozinhaRepository;
import com.souza.souzafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;

	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@CheckSecurity.Cozinhas.PodeConsultar //https://app.algaworks.com/aulas/2277/simplificando-o-controle-de-acesso-em-metodos-com-meta-anotacoes
	@Override
	@GetMapping
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		
		Page<Cozinha> cozinhasPage = cozinhaRepository
				.findAll(pageable);

		PagedModel<CozinhaModel> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(cozinhasPage,cozinhaModelAssembler);

		return cozinhasPagedModel;
	}

    @CheckSecurity.Cozinhas.PodeConsultar
	@Override
	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		return cozinhaModelAssembler.toModel(cozinha);
	}

    @CheckSecurity.Cozinhas.PodeEditar //https://app.algaworks.com/aulas/2277/simplificando-o-controle-de-acesso-em-metodos-com-meta-anotacoes
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler
				 .toDomainObject(cozinhaInput);
		cozinha = cadastroCozinha.salvar(cozinha);

		return cozinhaModelAssembler.toModel(cozinha);
	}

    @CheckSecurity.Cozinhas.PodeEditar
	@Override
	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {

		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);

		cozinhaInputDisassembler
		         .copyToDomainObject(cozinhaInput, cozinhaAtual);

		return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
	}

    @CheckSecurity.Cozinhas.PodeEditar
	@Override
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}

//	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//	public List<Cozinha> listar1() {
//		System.out.println("LISTAR 1");
//			return cozinhaRepository.listar();	
//	}
//	
//	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
//	public List<Cozinha> listar2() {
//		System.out.println("LISTAR 2");
//			return cozinhaRepository.listar();	
//	}
}
