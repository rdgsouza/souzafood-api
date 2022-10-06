package com.souza.souzafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.souza.souzafood.api.v1.SouzaLinks;
import com.souza.souzafood.core.security.SouzaSecurity;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	private SouzaLinks souzaLinks;

	@Autowired
	private SouzaSecurity souzaSecurity;    
	
//	https://app.algaworks.com/aulas/2191/implementando-o-root-entry-point-da-api
	@GetMapping
	public RootEntryPointModel root() {
	    var rootEntryPointModel = new RootEntryPointModel();
	    
	    if (souzaSecurity.podeConsultarCozinhas()) {
	        rootEntryPointModel.add(souzaLinks.linkToCozinhas("cozinhas"));
	    }
	    
	    if (souzaSecurity.podePesquisarPedidos()) {
	        rootEntryPointModel.add(souzaLinks.linkToPedidos("pedidos"));
	    }
	    
	    if (souzaSecurity.podeConsultarRestaurantes()) {
	        rootEntryPointModel.add(souzaLinks.linkToRestaurantes("restaurantes"));
	    }
	    
	    if (souzaSecurity.podeConsultarUsuariosGruposPermissoes()) {
	        rootEntryPointModel.add(souzaLinks.linkToGrupos("grupos"));
	        rootEntryPointModel.add(souzaLinks.linkToUsuarios("usuarios"));
	        rootEntryPointModel.add(souzaLinks.linkToPermissoes("permissoes"));
	    }
	    
	    if (souzaSecurity.podeConsultarFormasPagamento()) {
	        rootEntryPointModel.add(souzaLinks.linkToFormasPagamento("formas-pagamento"));
	    }
	    
	    if (souzaSecurity.podeConsultarEstados()) {
	        rootEntryPointModel.add(souzaLinks.linkToEstados("estados"));
	    }
	    
	    if (souzaSecurity.podeConsultarCidades()) {
	        rootEntryPointModel.add(souzaLinks.linkToCidades("cidades"));
	    }
	    
	    if (souzaSecurity.podeConsultarEstatisticas()) {
	        rootEntryPointModel.add(souzaLinks.linkToEstatisticas("estatisticas"));
	    }
	    
	    return rootEntryPointModel;
	}

	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
	}

}
