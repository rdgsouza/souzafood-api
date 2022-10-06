package com.souza.souzafood;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.souza.souzafood.domain.model.Cozinha;
import com.souza.souzafood.domain.repository.CozinhaRepository;
import com.souza.souzafood.domain.service.CadastroCozinhaService;
import com.souza.souzafood.util.DatabaseCleaner;
import com.souza.souzafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
//Vamos levantar o TomCat em uma porta aleatoria com a propiedade da anotação abaixo
//Essa anotação abaixo serve para levantar o TomCat levantar o Spring e o projeto souzafood-api
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//Essa anotação abaixo substirui o as propiedades do arquivo aplication.properties 
//OBS: Não substirui o arquivo do Spring continua usando o arquivo application.properties, quando executamos
//esta classe de teste é feita a substituição das propiedades so vai conter as propiedades que 
//estiverem nesse arquivo inclusive apagamos algumas propiedades porque não seram uteis neste momento.
//E para que isso? Por que precisamos de um banco de dados separado para fazer os testes na nossa api se
//usarmos um banco de dados para "brincarmos" com nossa api no caso quando estamos no ambiente de 
//desenvovilmente da nossa api e outro para testar a nossa api pode ser que quando estivermos fazendo os teste
//podemos apagar dados que não deveriam ser apagados ou vice e versa. Em fim separar responsabilidades
//fica muito melhor.
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

//Injetando a o numero da porta aleatoria que foi levantada com anotação acima para a propiedade abaixo
	@LocalServerPort
	private int port;
		
	@Autowired
	private DatabaseCleaner databaseclenear;
	
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private static final int COZINHA_ID_INEXISTENTE = 100;

	private Cozinha cozinhaAmericana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;
	
	//Método que é executado antes de cada teste permitindo assim que agente faça alguma coisa
	//Prepare o nosso teste de alguma forma antes de serem realmente executados
	@Before
	public void setUp() {
		//Vamos colocar o método estático abaixo por padrão para sempre habilitar os logs antes de cada 
  //execução de teste da classe 
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
 
		//Para não ficar duplicando em todos os testes passando o .port(port) no metodo estatico given dos
 //metodos de testes, então o RestAssured vai saber que por padrão a porta sempre vai ser o port passado abaixo
		RestAssured.port = port;
		//Colocamos o basePath abaixo para não especificar toda vez nos metodos o basePath 
		// dessa forma  .basePath("/cozinhas") então por padrão o RestAssured vai saber qual o path padrão
		RestAssured.basePath = "/cozinhas";	
		
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");
		
		databaseclenear.clearTables();
		prepararDados();
	}
	
	// Teste de API
	
	@Test
	public void deveRetornaStatus200_QuandoConsultarCozinha() {
		given() //dado a cituação abaixo
	     	.accept(ContentType.JSON)
        .when() //quando for
         	.get() // um get
        .then() //então
        	.statusCode(HttpStatus.OK.value()); //o que esperamos é um status 200 ok.		
	}

	
	@Test
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {		
		given() //dado a cituação abaixo
	     	.accept(ContentType.JSON)
        .when() //quando for
         	.get() // um get
        .then() //então
        	.body("", hasSize(quantidadeCozinhasCadastradas)); //tem que ter o que esperamos como resposta que é a
		// a quantidade de objetos passado pela propiedade quantidadeCozinhasCadastradas
       //	.body("nome", hasItems("Indiana","Tailandesa")); //Caso queira verificar se tem os nomes Indiana e Tailandesa
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		given()
		    .body(jsonCorretoCozinhaChinesa)//Propiedade jsonCorretoCozinhaChinesa contem o formato do json 
		    //ja retornado pelo metodo getContentFromResource antes passavamos a String do 
		    //objeto de cozinha chinesa diretamente no .body agora passamos pela propiedade
		    //jsonCorretoCozinhaChinesa dessa forma podemos formatar melhor o json em um arquivo externo
		    .contentType(ContentType.JSON)
		    .accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}	
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		given() 
		.pathParam("cozinhaId", cozinhaAmericana.getId())
     	.accept(ContentType.JSON)
    .when() 
     	.get("/{cozinhaId}")
    .then()
    	.statusCode(HttpStatus.OK.value())
    	.body("nome", equalTo(cozinhaAmericana.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given() 
		.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
     	.accept(ContentType.JSON)
    .when() 
     	.get("/{cozinhaId}")
    .then()
    	.statusCode(HttpStatus.NOT_FOUND.value());
		
	}
	
//Metodo responsavel por inserir uma massa de dados necessaria para os teste desta classe rodarem com sucesso
	private void prepararDados() {
	
	    Cozinha cozinhaTailandesa = new Cozinha();
	    cozinhaTailandesa.setNome("Tailandesa");
	    cozinhaRepository.save(cozinhaTailandesa);

	    cozinhaAmericana = new Cozinha();
	    cozinhaAmericana.setNome("Americana");
	    cozinhaRepository.save(cozinhaAmericana);
		
		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
		
	}
	
	// A forma abaixo mostra como fazer testes de integração

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {

		// cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		// ação
		assertTrue(TransactionSynchronizationManager.isActualTransactionActive());
		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		// validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
//
//	@Test(expected = ConstraintViolationException.class)
//	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setNome(null);
//
//		novaCozinha = cadastroCozinha.salvar(novaCozinha);
//	}
//
//	@Test(expected = EntidadeEmUsoException.class)
//	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
//		cadastroCozinha.excluir(1L);
//	}
//
//	@Test(expected = CozinhaNaoEncontradaException.class)
//	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
//		cadastroCozinha.excluir(100L);
//	}

}
