package com.souza.souzafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.souza.souzafood.api.SouzaFoodLinks;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	private SouzaFoodLinks souzaFoodLinks;

//	https://app.algaworks.com/aulas/2191/implementando-o-root-entry-point-da-api
	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();

		rootEntryPointModel.add(souzaFoodLinks.linkToCozinhas("cozinhas"));
		rootEntryPointModel.add(souzaFoodLinks.linkToPedidos("pedidos"));
		rootEntryPointModel.add(souzaFoodLinks.linkToRestaurantes("restaurantes"));
		rootEntryPointModel.add(souzaFoodLinks.linkToGrupos("grupos"));
		rootEntryPointModel.add(souzaFoodLinks.linkToUsuarios("usuarios"));
		rootEntryPointModel.add(souzaFoodLinks.linkToPermissoes("permissoes"));
		rootEntryPointModel.add(souzaFoodLinks.linkToFormasPagamento("formas-pagamentos"));
		rootEntryPointModel.add(souzaFoodLinks.linkToEstados("estados"));
		rootEntryPointModel.add(souzaFoodLinks.linkToCidades("cidades"));

		return rootEntryPointModel;
	}

	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
	}

}
