package com.souza.souzafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder //Builder é um padrão de projeto pra construir objeto em uma linguagem mas fluente.
//Pra mas informações veja essas duas aulas onde usamos o builder: https://www.algaworks.com/aulas/1931/tratando-excecoes-em-nivel-de-controlador-com-exceptionhandler
//e https://www.algaworks.com/aulas/1947/estendendo-o-formato-do-problema-para-adicionar-novas-propriedades
//e https://www.algaworks.com/aulas/1952/estendendo-o-problem-details-para-adicionar-as-propriedades-com-constraints-violadas
//e https://www.algaworks.com/aulas/1966/ajustando-exception-handler-para-adicionar-mensagens-de-validacao-em-nivel-de-classe
public class Problem {
	@ApiModelProperty(example = "400", position = 1)
	private Integer status;

	@ApiModelProperty(example = "2021-06-24T20:46:36Z", position = 5)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private OffsetDateTime timestamp;

	@ApiModelProperty(example = "https://souzafood.com.br/dados-invalidos", position = 10)
	private String type;

	@ApiModelProperty(example = "Dados inválidos", position = 15)
	private String title;

	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
			position = 20)
	private String detail;


	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
			position = 25)
	private String userMessage;

	@ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro. (Opcional)",
			position = 30)
	private List<Object> objects;

	@ApiModel("ObjetoProblema")
	@Getter
	@Builder
	public static class Object {

		@ApiModelProperty(example = "preco")
		private String name;

		@ApiModelProperty(example = "O preço é obrigatório")
		private String userMessage;
	}

}
