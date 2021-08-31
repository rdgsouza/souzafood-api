package com.souza.souzafood.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SouzaSecurity {

//	https://app.algaworks.com/aulas/2271/obtendo-usuario-autenticado-no-resource-server

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	} 
	
	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		
		return jwt.getClaim("usuario_id");
	}
}
