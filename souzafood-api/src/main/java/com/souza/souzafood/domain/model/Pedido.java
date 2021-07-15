package com.souza.souzafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.souza.souzafood.domain.event.PedidoCanceladoEvent;
import com.souza.souzafood.domain.event.PedidoConfirmadoEvent;
import com.souza.souzafood.domain.exception.NegocioException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false) //Sobre a propiedade callSuper = false, Aula: https://www.algaworks.com/aulas/2090/publicando-domain-events-a-partir-do-aggregate-root 
@Entity
// Nos usamos eventos para quando um pedido for confirmado logo em seguida ser enviado um e-mail
// Pra gente registrar um evento nos temos que herdar a classe AbstractAggregateRoot
// Aula: https://www.algaworks.com/aulas/2090/publicando-domain-events-a-partir-do-aggregate-root
public class Pedido extends AbstractAggregateRoot<Pedido>{

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo;
	
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;

	@Embedded
	private Endereco enderecoEntrega;

	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;

	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private OffsetDateTime dataCriacao;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private OffsetDateTime dataConfirmacao;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private OffsetDateTime dataCancelamento;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private OffsetDateTime dataEntrega;

	@ManyToOne(fetch = FetchType.LAZY)//Alteramos a estratégia de fetch para lazy porque nem sempre
	//nos vamos precisar da forma de pagamento na consulta por exemplo quando for consultar todas os
	//pedidos no não colocamos na entidade PedidoResumoModel para trazer as formas de pagamentos pois
	//é uma entidade que é usado para uma consulta de collection resource colocmos a forma de pagamento
	//apenas na entidade PedidoModel que é usada para consulta sigleton resorce.
	//Sendo assim podemos colocar a estratégia fetch LAZY
	//Caso tenha dúvida veja as aulas: https://www.algaworks.com/aulas/1904/entendendo-o-lazy-loading
	//e https://www.algaworks.com/aulas/2026/otimizando-a-query-de-pedidos-e-retornando-model-resumido-na-listagem
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;

	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente; 

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();
//	OBS*** No script de migração V007__criar-tabelas-pedido-item-pedido.sql temos uma chave única na 
//	tabela item_pedido:  unique key uk_item_pedido_produto (pedido_id, produto_id)
//	Essa chave única é para garantir que o usuário na hora do cadastro do pedido não haja repetição 
//	de produto várias vezes no itemPedido. Isso impede que o usuário cadastre, por exemplo, 
//	pedido 1132 com o item-pedido, produto = xsalada com quantidade 3 e 
//	logo depois adicione o mesmo item-pedido, produto = xsalada com quantidade 1, ao invés de cadastrar 
//	pedido 1132 adionando o item-pedido, produto = xsalada com quantidade 4. 
//	Então ao tentar cadastrar itens com produtos duplicados irá acontecer um erro de contrains. Para resolver isso vamos criar um método na classe EmissaoPedidoService chamado normalizarItens vamos normalizar os itens antes de percistir no banco de dados.
//	Isso gera uma melhor consistência nos dados, o que facilitaria, por exemplo, a realização de uma auditoria no sistema.
//  fonte resposta de Alexandre Moraes: https://www.algaworks.com/forum/topicos/83459/duvida-no-script-sql-do-conteudo-de-apoio#87766


	public void calcularValorTotal() {
	getItens().forEach(ItemPedido::calcularPrecoTotal);//Nesta linha é feito um forEach nos itens que estão no Pedido e pra cada item fazemos a multiplicação do valor unitário x quantidade com isso temos o preço total de cada item
		this.subtotal = getItens().stream() //Nesta linha o conjunto de itens é transformado em um stream de dados, que será percorrido em sua totalidade.
		.map(item -> item.getPrecoTotal()) //Nesta linha são extraídos todos os preços totais de cada item e é criado um vetor com apenas estes valores
		.reduce(BigDecimal.ZERO, BigDecimal::add);//Neste ponto é feita a agregação dos dados com o método reduce. O primeiro parâmetro é o valor inicial (BigDecimal.ZERO) e o segundo é um callback para o método add definido na classe BigDecimal.
//Toda a operação com o stream é feita para obter o valor total do pedido considerando apenas os valores dos itens presentes nos pedidos e armazenamento do valor no atributo subtotal.
		this.valorTotal = this.subtotal.add(this.taxaFrete);//A última linha é para definição do valor total do pedido, considerando os itens e a taxa de frete. Esta linha representa essa soma
//É utilizado novamente o método add nessa última linha porque ele ta presente na classe-tipo BigDecimal, visto que o atributo subtotal é desse tipo. Não é adequado utilizar o operador '+' para esse tipo. 
	  }
	
	public void confirmar() {
//	Metodo setStatus criado na classe pedido para não ser alterado o status de fora da classe
//	E foi chamado o metodo naoPodeAlterarPara para fazer uma verificação na regra de status 
//	que foi implementa no Enum StatusPedido.
//	Aula: https://www.algaworks.com/aulas/2030/refatorando-o-codigo-de-regras-para-transicao-de-status-de-pedidos
		  setStatus(StatusPedido.CONFIRMADO);
		  setDataConfirmacao(OffsetDateTime.now());

		  registerEvent(new PedidoConfirmadoEvent(this));
		  
	}

	public void entregar() {
		  setStatus(StatusPedido.ENTREGUE);
		  setDataEntrega(OffsetDateTime.now());
	}
	
	public void cancelar() {
		  setStatus(StatusPedido.CANCELADO);
		  setDataCancelamento(OffsetDateTime.now());
		  registerEvent(new PedidoCanceladoEvent(this));
	}
	
	public boolean podeSerConfirmado() {
		return getStatus().podeAlterarPara(StatusPedido.CONFIRMADO);
	}
	
	public boolean podeSerEntregue() {
		return getStatus().podeAlterarPara(StatusPedido.ENTREGUE);
	}
	
	public boolean podeSerCancelado() {
		return getStatus().podeAlterarPara(StatusPedido.CANCELADO);
	}
	
	private void setStatus(StatusPedido novoStatus) {
		
		if(getStatus().naoPodeAlterarPara(novoStatus)) {
			throw new NegocioException(
			 String.format("Status do pedido %s não pode ser alterado de %s para %s",
			   getCodigo(), getStatus().getDescricao(), novoStatus.getDescricao()));
		} 
		
		this.status = novoStatus;
	}
	
	@PrePersist
	private void gerarCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}
}
