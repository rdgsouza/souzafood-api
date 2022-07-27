package com.souza.souzafood;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.souza.souzafood.core.io.Base64ProcolResolver;
import com.souza.souzafood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
//Aula: https://app.algaworks.com/aulas/1884/implementando-um-repositorio-sdj-customizado
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class SouzafoodApiApplication {

	public static void main(String[] args) {
//		https://app.algaworks.com/aulas/1992/configurando-e-refatorando-o-projeto-para-usar-utc
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

//		https://app.algaworks.com/aulas/3627/externalizando-o-keystore-criando-um-protocolresolver-para-base64
		var app = new SpringApplication(SouzafoodApiApplication.class);
		app.addListeners(new Base64ProcolResolver());
		app.run(args);
//		SpringApplication.run(SouzafoodApiApplication.class, args);
	}

}
