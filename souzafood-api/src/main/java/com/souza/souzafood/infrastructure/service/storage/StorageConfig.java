package com.souza.souzafood.infrastructure.service.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.souza.souzafood.core.storage.StorageProperties;
import com.souza.souzafood.core.storage.StorageProperties.TipoStorage;
import com.souza.souzafood.domain.service.FotoStorageService;

@Configuration
public class StorageConfig {

	@Autowired
	private StorageProperties storageProperties;

	@Bean
//	Explicação da anotação @ConditionalOnProperty no conteúdo de apoio na aula: https://www.algaworks.com/aulas/2078/selecionando-a-implementacao-do-servico-de-storage-de-fotos
	@ConditionalOnProperty(name = "souzafood.storage.tipo", havingValue = "s3")
	public AmazonS3 amazons3() {
		var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(),
				storageProperties.getS3().getChaveAcessoSecreta());

		return AmazonS3ClientBuilder.standard().withCredentials(
				new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegiao()).build();
	}

//	aula: https://www.algaworks.com/aulas/2078/selecionando-a-implementacao-do-servico-de-storage-de-fotos
	@Bean
	public FotoStorageService fotoStorageService() {
		if (TipoStorage.S3.equals(storageProperties.getTipo())) {
			return new S3FotoStorageService();
		} else {
			return new LocalFotoStorageService();
		}
	}

}
