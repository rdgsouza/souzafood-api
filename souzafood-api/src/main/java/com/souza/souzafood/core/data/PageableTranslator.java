package com.souza.souzafood.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableTranslator {
//Aula: https://www.algaworks.com/aulas/2043/implementando-um-conversor-de-propriedades-de-ordenacao
	public static Pageable translate(Pageable pageable, Map<String, String> fieldsMapping) {

		var orders = pageable.getSort().stream()
			.filter(order -> fieldsMapping.containsKey(order.getProperty()))	
		   .map(order -> new Sort.Order(order.getDirection(), 
				   fieldsMapping.get(order.getProperty())))
		   .collect(Collectors.toList());
		   	
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by(orders));
	}
}
