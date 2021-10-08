package com.souza.souzafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.souza.souzafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome,
			BigDecimal taxaInicial, BigDecimal taxaFinal);

	List<Restaurante> findComFreteGratis(String nome);
}