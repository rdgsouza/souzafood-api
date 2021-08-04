package com.souza.souzafood.core.web;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private ApiRetirementHandler apiRetirementHandler;
	
//	https://app.algaworks.com/aulas/2099/habilitando-cors-globalmente-no-projeto-da-api
	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
		        .allowedMethods("*");
//		        .allowedOrigins("*")
//		        .maxAge(30);   
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	registry.addInterceptor(apiRetirementHandler);
	}
	
//	https://app.algaworks.com/aulas/2110/implementando-requisicoes-condicionais-com-shallow-etags
	@Bean
	public Filter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}

}
