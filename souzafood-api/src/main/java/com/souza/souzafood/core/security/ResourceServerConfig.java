package com.souza.souzafood.core.security;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		    .formLogin().loginPage("/login")
		    .and()
		    .authorizeRequests()
		        .antMatchers("/oauth/**").authenticated() //https://app.algaworks.com/aulas/2295/juntando-o-resource-server-com-o-authorization-server-no-mesmo-projeto
		        .and()
		    .csrf().disable() // Sobre o metodo .csrf() Aula: https://app.algaworks.com/aulas/2230/configurando-spring-security-com-http-basic
	                                                      // Aula: https://app.algaworks.com/aulas/1490/implementando-autenticacao-basic
		    .cors().and() // Sobre o metodo .cors() Aula: https://app.algaworks.com/aulas/2246/testando-o-fluxo-authorization-code-com-um-client-javascript
		    .oauth2ResourceServer()
		    .jwt()
		    .jwtAuthenticationConverter(jwtAuthenticationConverter());
	}

//	https://app.algaworks.com/aulas/2274/carregando-as-granted-authorities-e-restringindo-acesso-a-endpoints-na-api
	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
		   var authorities = jwt.getClaimAsStringList("authorities");
			
		   if (authorities == null) {
			   authorities = Collections.emptyList();
		   }
		   
//		   https://app.algaworks.com/aulas/2279/carregando-granted-authorities-dos-escopos-do-oauth2-no-resource-server
		   var scopesAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		   Collection<GrantedAuthority> grantedAutthorities = scopesAuthoritiesConverter.convert(jwt);
		   
		   grantedAutthorities.addAll(authorities.stream()
				   .map(SimpleGrantedAuthority::new)
				   .collect(Collectors.toList()));
		   
		   return grantedAutthorities;
		});
		
		return jwtAuthenticationConverter;
	}
	
	
//	https://app.algaworks.com/aulas/2295/juntando-o-resource-server-com-o-authorization-server-no-mesmo-projeto
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
//	Aula: https://app.algaworks.com/aulas/2260/configurando-o-resource-server-para-jwt-assinado-com-chave-simetrica
//	@Bean
//	public JwtDecoder jwtDecoder() {
//		var secretKey = new SecretKeySpec("KJNFUHWKFWKF98723721NQDLQNDQDJ81UNLDNadasldnasldnladas123".getBytes(), "HmacSHA256");
//		
//		return NimbusJwtDecoder.withSecretKey(secretKey).build();
//	}

}
