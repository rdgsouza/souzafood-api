package com.souza.souzafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.souza.souzafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>, 
		JpaSpecificationExecutor<Pedido> {

	Optional<Pedido> findByCodigo(String codigo);
	
//	Aula: https://app.algaworks.com/aulas/2026/otimizando-a-query-de-pedidos-e-retornando-model-resumido-na-listagem
	@Override
	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	List<Pedido> findAll();
	
//	https://app.algaworks.com/aulas/2285/desafio-restringindo-acessos-aos-endpoints-de-transicao-de-status-de-pedidos
	boolean isPedidoGerenciadoPor(String codigoPedido, Long usuarioId);

}
