package com.souza.souzafood.api.controller.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.validation.SmartValidator;

@Configuration
public class ControllerValidateUtilsConfiguration {

//	https://app.algaworks.com/forum/topicos/83704/duvida-na-criacao-de-uma-classe-util
	 public ControllerValidateUtilsConfiguration(SmartValidator validator) {
	        ControllerValidateUtil.setValidator(validator);
	    }
}
