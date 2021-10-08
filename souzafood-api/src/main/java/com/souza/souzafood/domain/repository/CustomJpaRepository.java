	package com.souza.souzafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

	Optional<T> buscarPrimeiro();
	
	void detach(T entity); // Método criado para resolver o erro de sincronização. Aula: https://www.algaworks.com/aulas/2017/implementando-regra-de-negocio-para-evitar-usuarios-com-e-mails-duplicados
	
}
