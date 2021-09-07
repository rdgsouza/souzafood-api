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
	
}
