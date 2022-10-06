package com.souza.souzafood.core.storage;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("souzafood.storage")
public class StorageProperties {

	private Local local = new Local();
	private S3 s3 = new S3();
	private TipoStorage tipo = TipoStorage.LOCAL;

	public enum TipoStorage {
		LOCAL, S3
	}

	@Getter
	@Setter
	public class Local {

		private Path diretorioFotos;
	}

	@Getter
	@Setter
	public class S3 {

		private String idChaveAcesso;
		private String chaveAcessoSecreta;
		private String bucket;
		private Regions regiao; // Na propiedade regiao alteramos para Regions da AWS que é um ENUM
//		porque facilita no application.properties a busca pelas regiões disponiveis pela AWS
//		Fazendo isso o Spring consegue converter a string que passamos na propiedade do application.properties
//		para um tipo regions
//		Aula: algaworks.com/aulas/2074/definindo-bean-do-client-da-amazon-s3-e-configurando-credenciais
		private String diretorioFotos;
		private String urlBuket;
	}

}
