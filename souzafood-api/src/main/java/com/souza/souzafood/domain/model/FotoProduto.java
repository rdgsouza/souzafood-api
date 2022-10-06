package com.souza.souzafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto {

	@EqualsAndHashCode.Include
	@Id
	@Column(name = "produto_id")
	private Long id;
//  Aula: https://www.algaworks.com/aulas/2057/mapeando-entidade-fotoproduto-e-relacionamento-um-para-um
//	Link como resolver o problema do lazy loading com @OneToOne
//	https://blog.algaworks.com/lazy-loading-com-mapeamento-onetoone/
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Produto produto;

	private String nomeArquivo;
	private String descricao;
	
	private String contentType;
	private Long tamanho;

	public Long getRestauranteId() {
		if (getProduto() != null) {

			return getProduto().getRestaurante().getId();
		}
		
		return null;
	}

}
