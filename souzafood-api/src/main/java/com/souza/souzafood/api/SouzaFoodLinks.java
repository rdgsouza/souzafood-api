package com.souza.souzafood.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.controller.CidadeController;
import com.souza.souzafood.api.controller.CozinhaController;
import com.souza.souzafood.api.controller.EstadoController;
import com.souza.souzafood.api.controller.FluxoPedidoController;
import com.souza.souzafood.api.controller.FormaPagamentoController;
import com.souza.souzafood.api.controller.PedidoController;
import com.souza.souzafood.api.controller.RestauranteController;
import com.souza.souzafood.api.controller.RestauranteFormaPagamentoController;
import com.souza.souzafood.api.controller.RestauranteProdutoController;
import com.souza.souzafood.api.controller.RestauranteUsuarioResponsavelController;
import com.souza.souzafood.api.controller.UsuarioController;
import com.souza.souzafood.api.controller.UsuarioGrupoController;

// https://app.algaworks.com/aulas/2175/refatorando-construcao-e-inclusao-de-links-em-representation-model
@Component
public class SouzaFoodLinks {

	  public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
      		new TemplateVariable("page", VariableType.REQUEST_PARAM),
      		new TemplateVariable("size", VariableType.REQUEST_PARAM),
      		new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	  public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
				new TemplateVariable("projecao", VariableType.REQUEST_PARAM)); 
	  
	public Link linkToPedidos(String rel) {
	        TemplateVariables filtroVariables = new TemplateVariables(
	        		new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
	        		new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
	        		new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
	        		new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
	           
	        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
	        
	    //  https://app.algaworks.com/forum/topicos/84494/link-uritemplate-template-string-rel-depreciado
        // pedidoModel.add(Link.of(UriTemplate.of(pedidosUrl, pageVariables), "pedidos"));
	               return Link.of(UriTemplate.of(pedidosUrl, 
	        	   PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
	}
	
	public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
		
		return linkTo(methodOn(FluxoPedidoController.class).confirmar(codigoPedido))
				.withRel(rel);
	}

	public Link linkToEntregaPedido(String codigoPedido, String rel) {
		
		return linkTo(methodOn(FluxoPedidoController.class).entregar(codigoPedido))
				.withRel(rel); 
	}
	
	public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
		
		return linkTo(methodOn(FluxoPedidoController.class).cancelar(codigoPedido))
				.withRel(rel);
	}
	
	public Link linkToRestaurante(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestauranteController.class)
	            .buscar(restauranteId)).withRel(rel);
	}

	public Link linkToRestaurante(Long restauranteId) {
	    return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestaurantes(String rel) {
		String restaurantesUrl = linkTo(RestauranteController.class).toUri().toString();
		
		 return Link.of(UriTemplate.of(restaurantesUrl, 
	        	   PAGINACAO_VARIABLES.concat(PROJECAO_VARIABLES)), rel);
	}

	public Link linkToRestaurantes() {
	    return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToUsuario(Long usuarioId, String rel) {
	    return linkTo(methodOn(UsuarioController.class)
	            .buscar(usuarioId)).withRel(rel);
	}

	public Link linkToUsuario(Long usuarioId) {
	    return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	public Link linkToUsuarios(String rel) {
	    return linkTo(UsuarioController.class).withRel(rel);
	}

	public Link linkToUsuarios() {
	    return linkToUsuarios(IanaLinkRelations.SELF.value());
	}

	public Link linkToGruposUsuario(Long usuarioId, String rel) {
	    return linkTo(methodOn(UsuarioGrupoController.class)
	            .listar(usuarioId)).withRel(rel);
	}

	public Link linkToGruposUsuario(Long usuarioId) {
	    return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteResponsaveis(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
				.listar(restauranteId)).withRel(rel);
	}
	
	public Link linkToResponsaveisRestaurante(Long restauranteId) {
		return linkToRestauranteResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestauranteFormaPagamentoController.class)
	            .listar(restauranteId)).withRel(rel);
	}

	public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
	    return linkTo(methodOn(FormaPagamentoController.class)
	            .buscar(formaPagamentoId, null)).withRel(rel);
	}

	public Link linkToFormaPagamento(Long formaPagamentoId) {
	    return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCidade(Long cidadeId, String rel) {
	    return linkTo(methodOn(CidadeController.class)
	            .buscar(cidadeId)).withRel(rel);
	}

	public Link linkToCidade(Long cidadeId) {
	    return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCidades(String rel) {
	    return linkTo(CidadeController.class).withRel(rel);
	}

	public Link linkToCidades() {
	    return linkToCidades(IanaLinkRelations.SELF.value());
	}

	public Link linkToEstado(Long estadoId, String rel) {
	    return linkTo(methodOn(EstadoController.class)
	            .buscar(estadoId)).withRel(rel);
	}

	public Link linkToEstado(Long estadoId) {
	    return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToEstados(String rel) {
	    return linkTo(EstadoController.class).withRel(rel);
	}

	public Link linkToEstados() {
	    return linkToEstados(IanaLinkRelations.SELF.value());
	}

	public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
	    return linkTo(methodOn(RestauranteProdutoController.class)
	            .buscar(restauranteId, produtoId))
	            .withRel(rel);
	}

	public Link linkToProduto(Long restauranteId, Long produtoId) {
	    return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCozinha(Long cozinhaId, String rel) {
	    return linkTo(methodOn(CozinhaController.class)
	            .buscar(cozinhaId)).withRel(rel);
	}

	public Link linkToCozinha(Long cozinhaId) {
	    return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
	}  
	
	public Link linkToCozinhas(String rel) {
	    return linkTo(CozinhaController.class).withRel(rel);
	}

	public Link linkToCozinhas() {
	    return linkToCozinhas(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestauranteAbertura(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestauranteController.class)
	            .abrir(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteFechamento(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestauranteController.class)
	            .fechar(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteInativacao(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestauranteController.class)
	            .inativar(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestauranteController.class)
	            .ativar(restauranteId)).withRel(rel);
	}
	
}
