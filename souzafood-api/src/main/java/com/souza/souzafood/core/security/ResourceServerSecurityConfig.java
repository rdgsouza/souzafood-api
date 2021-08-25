package com.souza.souzafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ResourceServerSecurityConfig extends WebSecurityConfigurerAdapter {	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	      http
	       .authorizeRequests()
	          .anyRequest().authenticated()
	       .and()
	       .cors().and() //Aula: https://app.algaworks.com/aulas/2246/testando-o-fluxo-authorization-code-com-um-client-javascript
	       .oauth2ResourceServer().opaqueToken();
	}
	
}
