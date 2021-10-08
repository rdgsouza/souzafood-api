package com.souza.souzafood.api.controller.util;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

import com.souza.souzafood.core.validation.ValidacaoException;

public class ControllerValidateUtil {

	private static Validator validator;

	public static void setValidator(Validator validator) {
		ControllerValidateUtil.validator = validator;
	}

//	https://app.algaworks.com/aulas/1967/executando-processo-de-validacao-programaticamente
	public static void validate(Object object, String objectName) {

		BeanPropertyBindingResult bindingResult =
				new BeanPropertyBindingResult(object.getClass(), objectName);

		validator.validate(object, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}

	}
}
