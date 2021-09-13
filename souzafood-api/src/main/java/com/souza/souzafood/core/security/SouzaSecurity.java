package com.souza.souzafood.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.souza.souzafood.domain.repository.PedidoRepository;
import com.souza.souzafood.domain.repository.RestauranteRepository;

@Component
public class SouzaSecurity {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
//	https://app.algaworks.com/aulas/2271/obtendo-usuario-autenticado-no-resource-server
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	} 
	
	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		
		return jwt.getClaim("usuario_id");
	}
	
//	https://app.algaworks.com/aulas/2282/restringindo-acessos-de-forma-contextual-sensivel-a-informacao
	public boolean gerenciaRestaurante(Long restauranteId) {
	    if (restauranteId == null) {
	        return false;
	    }
	    
	    return restauranteRepository.existsResponsavel(restauranteId, getUsuarioId());
	}        

	public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
	    return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
	}    
	
	public boolean usuarioAutenticadoIgual(Long usuarioId) {
		return getUsuarioId() != null && usuarioId != null
				&& getUsuarioId().equals(usuarioId);
	}
	
//	https://app.algaworks.com/aulas/2293/gerando-links-do-hal-dinamicamente-de-acordo-com-permissoes-do-usuario
	public boolean hasAuthority(String authorityName) {
		return getAuthentication().getAuthorities().stream()
				 .anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}
	
	public boolean podeGerenciarPedidos(String codigoPedido) {
		return hasAuthority("SCOPE_WRITE") && (hasAuthority("GERENCIAR_PEDIDOS")
				|| gerenciaRestauranteDoPedido(codigoPedido));
	}
	
//	https://app.algaworks.com/aulas/2294/desafio-gerando-links-do-hal-dinamicamente-de-acordo-com-permissoes
	public boolean isAutenticado() {
	    return getAuthentication().isAuthenticated();
	}
	
//	https://app.algaworks.com/aulas/2294/desafio-gerando-links-do-hal-dinamicamente-de-acordo-com-permissoes
	public boolean temEscopoEscrita() {
	    return hasAuthority("SCOPE_WRITE");
	}

//  https://app.algaworks.com/aulas/2294/desafio-gerando-links-do-hal-dinamicamente-de-acordo-com-permissoes
	public boolean temEscopoLeitura() {
	    return hasAuthority("SCOPE_READ");
	}
	
	public boolean podeConsultarRestaurantes() {
	    return temEscopoLeitura() && isAutenticado();
	}
	
	public boolean podeGerenciarCadastroRestaurantes() {
	    return temEscopoEscrita() && hasAuthority("EDITAR_RESTAURANTES");
	}
	
	public boolean podeGerenciarFuncionamentoRestaurantes(Long restauranteId) {
	    return temEscopoEscrita() && (hasAuthority("EDITAR_RESTAURANTES")
	            || gerenciaRestaurante(restauranteId));
	}
	
	public boolean podeConsultarUsuariosGruposPermissoes() {
	    return temEscopoLeitura() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
	}
	
	public boolean podeEditarUsuariosGruposPermissoes() {
	    return temEscopoEscrita() && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
	}
	
	public boolean podePesquisarPedidos(Long clienteId, Long restauranteId) {
	    return temEscopoLeitura() && (hasAuthority("CONSULTAR_PEDIDOS")
	            || usuarioAutenticadoIgual(clienteId) || gerenciaRestaurante(restauranteId));
	}
	
	public boolean podePesquisarPedidos() {
	    return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarFormasPagamento() {
	    return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarCidades() {
	    return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarEstados() {
	    return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarCozinhas() {
	    return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarEstatisticas() {
	    return temEscopoLeitura() && hasAuthority("GERAR_RELATORIOS");
	}  
	
	
	
}
