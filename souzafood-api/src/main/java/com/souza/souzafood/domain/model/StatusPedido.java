package com.souza.souzafood.domain.model;

import java.util.Arrays;
import java.util.List;

// Aula: https://www.algaworks.com/aulas/2030/refatorando-o-codigo-de-regras-para-transicao-de-status-de-pedidos
public enum StatusPedido {

	CRIADO("Criado"),
	CONFIRMADO("Confirmado",CRIADO),
	ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO);

	private String descricao;
	private List<StatusPedido> statusAnteriores;

	private StatusPedido(String descricao, StatusPedido... statusAnteriores) {
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}

	public String getDescricao() {
		return this.descricao;
	}
	
	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		return !novoStatus.statusAnteriores.contains(this);// Se não tiver na lista não pode alterar o status
	}
	
	public boolean podeAlterarPara(StatusPedido novoStatus) {
		return !naoPodeAlterarPara(novoStatus);
	}
	
}
