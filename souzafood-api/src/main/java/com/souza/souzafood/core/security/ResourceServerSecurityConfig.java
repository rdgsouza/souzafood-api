package com.souza.souzafood.core.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
public class ResourceServerSecurityConfig extends WebSecurityConfigurerAdapter {	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	      http
	       .authorizeRequests()
	          .anyRequest().authenticated()
	       .and()
	       .cors().and() // Sobre o metodo cors(). Aula: https://app.algaworks.com/aulas/2246/testando-o-fluxo-authorization-code-com-um-client-javascript
	       .oauth2ResourceServer().jwt();
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
		var secretKey = new SecretKeySpec("KJNFUHWKFWKF98723721NQDLQNDQDJ81UNLDNadasldnasldnladas123".getBytes(), "HmacSHA256");
		
		return NimbusJwtDecoder.withSecretKey(secretKey).build();
	}
	
}
