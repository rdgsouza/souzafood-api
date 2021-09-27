package com.souza.souzafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

// Não vamos mas usar a sobrecarga abaixo porque estamos usando o campo codigo do
// pedido para fazer a busca e esse campo é uma staing então implementamos de outra forma
//	public PedidoNaoEncontradoException(String mensagem) {
//		super(mensagem);
//	}
//
//	public PedidoNaoEncontradoException(Long pedidoId) {
//		this(String.format("Não existe um pedido com código %d", pedidoId));
//	}

	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format("Não existe um pedido com código %s", codigoPedido));
	}
	
}
