package com.souza.souzafood.core.web;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
//	https://app.algaworks.com/aulas/2110/implementando-requisicoes-condicionais-com-shallow-etags
	@Bean
	public Filter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}

//	https://app.algaworks.com/aulas/2221/depreciando-uma-versao-da-api
//	@Autowired
//	private ApiRetirementHandler apiRetirementHandler;

//	Tiramos a configuracao abaixo na aula: https://app.algaworks.com/aulas/2295/juntando-o-resource-server-com-o-authorization-server-no-mesmo-projeto
	
//	https://app.algaworks.com/aulas/2099/habilitando-cors-globalmente-no-projeto-da-api
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//
//		registry.addMapping("/**")
//  		       .allowedMethods("*");
//	    	       .allowedOrigins("*")
//		           .maxAge(30);   
//	}

//	https://app.algaworks.com/aulas/2221/depreciando-uma-versao-da-api
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//	registry.addInterceptor(apiRetirementHandler);
//	}

}
