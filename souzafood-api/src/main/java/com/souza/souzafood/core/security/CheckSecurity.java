package com.souza.souzafood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RUNTIME)
@Target(METHOD)
public @interface CheckSecurity {

	public @interface Cozinhas {

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar {}

		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {}

	}

	public @interface Restaurantes {

		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarCadastro {}

		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and "
				+ "(hasAuthority('EDITAR_RESTAURANTES') or "
				+ "@souzaSecurity.gerenciaRestaurante(#restauranteId))")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarFuncionamento {}

		
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {}

	}

	public @interface Pedidos {
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or " //https://app.algaworks.com/aulas/2283/restringindo-acessos-com-postauthorize
				+ "@souzaSecurity.getUsuarioId() == returnObject.cliente.id or "
				+ "@souzaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeBuscar {}

		@PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULTAR_PEDIDOS') or " 
				+ "@souzaSecurity.getUsuarioId() == #filtro.clienteId or"
				+ "@souzaSecurity.gerenciaRestaurante(#filtro.restauranteId))")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodePesquisar { }
		
	}
	
}
