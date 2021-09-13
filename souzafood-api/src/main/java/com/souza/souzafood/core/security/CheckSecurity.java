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

        @PreAuthorize("@souzaSecurity.podeConsultarCozinhas()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {}

	}

	public @interface Restaurantes {

		@PreAuthorize("@souzaSecurity.podeGerenciarCadastroRestaurantes()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarCadastro {}

		
        @PreAuthorize("@souzaSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarFuncionamento {}

		
        @PreAuthorize("@souzaSecurity.podeConsultarRestaurantes()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {}

	}

	public @interface Pedidos {
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or " //https://app.algaworks.com/aulas/2283/restringindo-acessos-com-postauthorize
				+ "@souzaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or " //https://app.algaworks.com/aulas/2292/corrigindo-logica-de-restricao-de-acessos-para-client-credentials-flow
				+ "@souzaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeBuscar {}

        @PreAuthorize("@souzaSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodePesquisar { }
	
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeCriar { }

//		https://app.algaworks.com/aulas/2285/desafio-restringindo-acessos-aos-endpoints-de-transicao-de-status-de-pedidos
//		@PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('GERENCIAR_PEDIDOS') or "
//				+ "@souzaSecurity.gerenciaRestauranteDoPedido(#codigoPedido))")
//		https://app.algaworks.com/aulas/2293/gerando-links-do-hal-dinamicamente-de-acordo-com-permissoes-do-usuario
		@PreAuthorize("@souzaSecurity.podeGerenciarPedidos(#codigoPedido)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarPedidos { }
		
	}
	
	public @interface FormasPagamento {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }

        @PreAuthorize("@souzaSecurity.podeConsultarFormasPagamento()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
		    
	}
	
	public @interface Cidades {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }

        @PreAuthorize("@souzaSecurity.podeConsultarCidades()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface Estados {
	    
	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }

        @PreAuthorize("@souzaSecurity.podeConsultarEstados()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface UsuariosGruposPermissoes {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
	            + "@souzaSecurity.usuarioAutenticadoIgual(#usuarioId)")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeAlterarPropriaSenha { }
	    
	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
	            + "@souzaSecurity.usuarioAutenticadoIgual(#usuarioId))")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeAlterarUsuario { }

        @PreAuthorize("@souzaSecurity.podeEditarUsuariosGruposPermissoes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }
	    

        @PreAuthorize("@souzaSecurity.podeConsultarUsuariosGruposPermissoes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface Estatisticas {

        @PreAuthorize("@souzaSecurity.podeConsultarEstatisticas()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
}
