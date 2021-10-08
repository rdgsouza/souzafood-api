package com.souza.souzafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.souza.souzafood.domain.model.Restaurante;

public class RestauranteSpecs {

	public static Specification<Restaurante> comFreteGratis() {
	
		
  //return new RestauranteComFreteGratisSpec();	
	
// Vamos fazer o retorno de outra forma sem usar a classe RestauranteComFreteGratisSpec 
// na instanciação no retorno. A idéia é não precisar criar uma classe que implemente a interface
// Specification e sim ter apenas uma classe como esta retornando o Specification diretamente nos
// seus metodos.
// Uma inteface do tipo Specification retorna um metodo toPredicate e esse metodo recebe 
// por parametro: Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder
// Ficando assim:
// Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
// Então criamos um expressão lambda onde vai ser recebido como parametros o root, query e builder
// assim como no metodo toPredicate. E no corpo da instancia a ser retornada, retornamos um predicate
// apartir do builder
// Dessa forma temos um metodo "toPredicate" retornado para o Specification
// Podemos até apagar a classe RestauranteComFreteGratisSpec pois ela não é mas útil já que agora
// Usamos essa classe RestauranteSpecs e nela ja retornamos o Specification diretamente nos metodos
		
	  return (root, query, builder) -> //Operador
		  builder.equal(root.get("taxaFrete"), BigDecimal.ZERO); //corpo da instancia a ser retornada	
			}
		//OBS: O BigDecimal.ZERO representa uma constante zero
	
	public static Specification<Restaurante> comNomeSemelhante(String nome) {
			
   //return new RestauranteComNomeSemelhanteSpec(nome);
		
	  return (root, query, builder) -> 
		  builder.like(root.get("nome"), "%" + nome + "%");	
	}
}
