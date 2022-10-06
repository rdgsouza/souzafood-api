package com.souza.souzafood.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.souza.souzafood.domain.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
        implements CustomJpaRepository<T, ID> {

//	Aula: https://app.algaworks.com/aulas/1884/implementando-um-repositorio-sdj-customizado
	
	private EntityManager manager;

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, 
			EntityManager entityManager) {
		super(entityInformation, entityManager);

		this.manager = entityManager;

	}

	@Override
	public Optional<T> buscarPrimeiro() {
		var jpql = "from " + getDomainClass().getName();

		T entity = manager.createQuery(jpql, getDomainClass()).setMaxResults(1)
				.getSingleResult();

		return Optional.ofNullable(entity); // Retorna um optional que descreve o valor
//	fornecido, caso estiver o valor estiver null, retorna um optional vazio.

	}

	@Override
	public void detach(T entity) {
     manager.detach(entity);		
	}

}
