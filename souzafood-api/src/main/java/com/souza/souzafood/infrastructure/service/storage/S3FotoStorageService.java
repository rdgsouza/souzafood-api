package com.souza.souzafood.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.souza.souzafood.core.storage.StorageProperties;
import com.souza.souzafood.domain.service.FotoStorageService;

public class S3FotoStorageService implements FotoStorageService {

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
		String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());

		var objectMetada = new ObjectMetadata();
        objectMetada.setContentType(novaFoto.getContentType());
		
		// Preparando o paylod para chamada que vamos fazer na api da amazon
		var putObjectRequest = new PutObjectRequest(
				storageProperties.getS3()
				.getBucket(), 
				caminhoArquivo,
				novaFoto
				.getInputStream(),
				objectMetada)
				.withCannedAcl(CannedAccessControlList.PublicRead);//Propiedade para quando fizer
                //uma requisição para colocar o objeto na S3, coloca tbm a permissao de public read ou seja de leitura publica
		        //dessa forma a url do objeto pode ser acessado publicamente

		// fazendo a chamada na amazon
		amazonS3.putObject(putObjectRequest);
		
		} catch (Exception e) {

        throw new StorageException("Não foi possível enviar arquivo para Amazon S3.", e);
	   
		}
	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
		
		FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
				.url(url.toString()).build();
		
		return fotoRecuperada;
	}

	@Override
	public void remover(String nomeArquivo) {

		try {
			String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
			
		var deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(),
				caminhoArquivo);
		
		amazonS3.deleteObject(deleteObjectRequest);
		
		} catch (Exception e) {

        throw new StorageException("Não foi possível deletar arquivo na Amazon S3.", e);
	   
		}
	}

	private String getCaminhoArquivo(String nomeArquivo) {
			return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}

}
