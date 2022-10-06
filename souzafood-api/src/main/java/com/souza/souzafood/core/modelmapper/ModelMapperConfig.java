package com.souza.souzafood.core.modelmapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.souza.souzafood.api.model.input.ItemPedidoInput;
import com.souza.souzafood.api.v1.model.EnderecoModel;
import com.souza.souzafood.api.v1.model.UrlFotoProdutoModel;
import com.souza.souzafood.api.v2.model.input.CidadeInputV2;
import com.souza.souzafood.core.storage.StorageProperties;
import com.souza.souzafood.core.storage.StorageProperties.TipoStorage;
import com.souza.souzafood.domain.model.Cidade;
import com.souza.souzafood.domain.model.Endereco;
import com.souza.souzafood.domain.model.FotoProduto;
import com.souza.souzafood.domain.model.ItemPedido;

//https://app.algaworks.com/aulas/2001/customizando-o-mapeamento-de-propriedades-com-modelmapper
//https://app.algaworks.com/aulas/2002/mapeando-para-uma-instancia-destino-e-nao-um-tipo-com-modelmapper
//https://app.algaworks.com/aulas/2012/adicionando-endereco-no-modelo-da-representacao-do-recurso-de-restaurante
@Configuration
public class ModelMapperConfig {

	@Autowired
	private StorageProperties storageProperties;

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
.addMappings(mapper -> mapper.skip(Cidade::setId));
		
//Para forçar o map a fazer um mapping em uma propriedade com nome diferente
//como vimos na aula sobre correspondência as propiedades tem que ter uma correspondencia para poder ser passado os valores para de uma propiedade para outra
//Mas para ser "copiado" os valores de uma propiedade para uma outra que não tem correspondêcia nos nomes. 
//Temos que fazer como dessa forma:
//	 modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//	.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
//Fizemos isso em um exemplo quando nos queriamos um precoFrete em vez de taxaFrete na entidade RestauranteModel
//Usamos o modelMapper.createTypeMap para força a cópia das propiedades que não tem correspondêcias	

//Na classe ItemPedidoInput temos a propiedade produtoId o que acontece é que na hora de fazer a cópia
//dessa propiedade para entidade ItemPedido ele é atribuido a propiedade id da entidade ItemPedido
//pelo fato de modelMapper usar a estrategia padrão de correspondência ele acaba achando que é para ser atribuido		
//ai vai dar uma exception dando um problema falando que não pode salvar um detached item pedido. Ou seja um itemPedido desanexado
//E ta errado porque o id do itemPedido é AUTO INCREMENT. Então nos atribuimos um id para propiedade produtoId da classe ItemPedidoInput 
//mas não podemos deixar que o modellMapper intenda que é o id itemPedido e tentar atribuir na hora hora da copia
//Para resolver esse problema fizemos da seguinte forma:
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)// Faz o mapeamento de ItemPedidoInput para
																			// ItemPedido
				.addMappings(mapper -> mapper.skip(ItemPedido::setId));// Mas faz um (skip = pula) ignora o mapeamento
																		// para o setId ou seja não chama o setId da
																		// entidade
//ItemPedido. Quando fazemos isso estamos dizendo para o modelMapper não atribuir aquele produtoId para ele não ir para o id de ItemPedido com isso evitamos a exception: não pode salvar um detached item pedido

//Aqui fizemos o uso do enderecoToEnderecoModelTypeMap de uma forma mas profunda porque precisamos
//pegar uma propiedade nome que estar dentro da entidade. Porque não queremos na representação
//RestauranteModel o endereço aninhado com estado queremos apenas o nome do estado para isso fizemos essa busca		
//que no final vair apenas pegar o nome do estado para passar para propiedade que não é mais uma propiedade de instância do tipo estado e sim uma propiedade
//do tipo String na entidade CidadeResumoModel		
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

		enderecoToEnderecoModelTypeMap.<String>addMapping(src -> src.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

		Converter<String, String> imagemParaUrl = ctx -> criarImagemUrl(ctx.getSource());

		modelMapper.createTypeMap(FotoProduto.class, UrlFotoProdutoModel.class).addMappings(
				mapper -> mapper.using(imagemParaUrl).map(FotoProduto::getNomeArquivo, UrlFotoProdutoModel::setUrl));

		return modelMapper;
	}

	private String criarImagemUrl(String nomeArquivo) {
		if (TipoStorage.S3.equals(storageProperties.getTipo())) {
			return getCaminhoArquivoS3(nomeArquivo);
		} else {
			return getCaminhoArquivoLocal(nomeArquivo);
		}
	}

	private String getCaminhoArquivoS3(String nomeArquivo) {
		return String.format("%s/%s/%s", storageProperties.getS3().getUrlBuket(),
				storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}

	// Metodo para retorna o caminho do arquivo quando for armazenado no disco local
	private String getCaminhoArquivoLocal(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getLocal().getDiretorioFotos(), nomeArquivo);
	}

}
