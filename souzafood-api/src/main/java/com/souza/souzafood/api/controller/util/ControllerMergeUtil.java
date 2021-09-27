package com.souza.souzafood.api.controller.util;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ControllerMergeUtil {
	
//	https://app.algaworks.com/aulas/1967/executando-processo-de-validacao-programaticamente
	public static void merge(Map<String, Object> dadosOrigem, 
			Object objetoDestino, HttpServletRequest request) {

		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

			Object objetoOrigem = objectMapper.convertValue(dadosOrigem, objetoDestino.getClass());

			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {

				Field field = ReflectionUtils.findField(objetoDestino.getClass(), nomePropriedade);
				field.setAccessible(true);

				Object novoValor = ReflectionUtils.getField(field, objetoOrigem);

				// System.out.println(nomePropriedade + " = " + valorPropriedade);

				ReflectionUtils.setField(field, objetoDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);

		}
	}

}
