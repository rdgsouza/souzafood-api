package com.souza.souzafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.souza.souzafood.api.openapi.controller.FluxoPedidoControllerOpenApi;
import com.souza.souzafood.api.v1.assembler.PedidoStatusResumoModelAssembler;
import com.souza.souzafood.api.v1.model.PedidoStatusResumoModel;
import com.souza.souzafood.core.security.CheckSecurity;
import com.souza.souzafood.domain.service.EmissaoPedidoService;
import com.souza.souzafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping(path = "/v1/pedidos/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

	@Autowired
	private FluxoPedidoService fluxoPedido;

	@Autowired
	private PedidoStatusResumoModelAssembler pedidoStatusResumoModelAssembler;
	
	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@CheckSecurity.Pedidos.PodeGerenciarPedidos
	@Override
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
		fluxoPedido.confirmar(codigoPedido);
		
		return ResponseEntity.noContent().build(); //https://app.algaworks.com/aulas/2177/adicionando-links-de-transicoes-de-status-de-pedidos
	}

	@CheckSecurity.Pedidos.PodeGerenciarPedidos
	@Override
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
	    fluxoPedido.cancelar(codigoPedido);
	    
	    return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Pedidos.PodeGerenciarPedidos
	@Override
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
	    fluxoPedido.entregar(codigoPedido);
	    
	    return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Pedidos.PodeGerenciarPedidos
	@GetMapping("/status")
	@Override
	public PedidoStatusResumoModel buscar(@PathVariable String codigoPedido) {
		return pedidoStatusResumoModelAssembler.toModel(
			emissaoPedidoService.buscarOuFalhar(codigoPedido));
	}
}
