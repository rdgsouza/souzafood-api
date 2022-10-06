package com.souza.souzafood.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

//https://app.algaworks.com/aulas/1962/usando-o-resource-bundle-do-spring-como-resource-bundle-do-bean-validation
	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {

		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);

		return bean;
	}
}
