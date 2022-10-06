package com.souza.souzafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.souza.souzafood.domain.model.Restaurante;

 public interface RestauranteRepository
         extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, 
         JpaSpecificationExecutor<Restaurante> {
	
// Quando realizamos um findAll(), penso que é intuitivo que o resultado desta lista seja itens 
// não repetidos, pelo menos na maioria das vezes nesse caso temos que colocar um distinct na jpql.
// devido ao join que é feito na tabela intermediaria restaurate_forma_pagamento é adicionado mas
// registros no retorno do resultado entao não queremos que inclua registros a mas.	 
// Desta forma, acho interessante deixar o mérito do processamento desta lista distinta para 
// o banco de dados, sendo melhor usar o distinct
// Aula: https://www.algaworks.com/aulas/1907/resolvendo-o-problema-do-n1-com-fetch-join-na-jpql?pagina=0
// Resposta: https://www.algaworks.com/forum/topicos/79816/errata

//    @Query("select distinct r from Restaurante r left join fetch r.cozinha left join fetch r.formasPagamento")
	  @Override
	@Query("select distinct r from Restaurante r left join fetch r.cozinha")
	 List<Restaurante> findAll(); 
	 
    List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

//    @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);
	
//	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	List<Restaurante> findTop2ByNomeContaining(String nome);

	int countByCozinhaId(Long cozinha);	

	boolean existsResponsavel(Long restauranteId, Long usuarioId);
}
