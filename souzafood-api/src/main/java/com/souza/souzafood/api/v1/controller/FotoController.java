package com.souza.souzafood.api.v1.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.souza.souzafood.domain.exception.EntidadeNaoEncontradaException;
import com.souza.souzafood.domain.model.FotoProduto;
import com.souza.souzafood.domain.service.CatalagoFotoProdutoService;
import com.souza.souzafood.domain.service.FotoStorageService;
import com.souza.souzafood.domain.service.FotoStorageService.FotoRecuperada;

import springfox.documentation.annotations.ApiIgnore;

//Solução implementada para listar fotos de todos os produtos de um restaurante
// Fórum AlgaWorks: https://www.algaworks.com/forum/topicos/84056/lista-de-fotos-curiosidade
@ApiIgnore //Anotação para ignorar esse controller na documentação //Exemplo na aula: https://app.algaworks.com/aulas/2191/implementando-o-root-entry-point-da-api
@Controller
@RequestMapping("/v1/home/rodrigo/Documents/catalago/{nomeArquivo}")
public class FotoController {

	@Autowired
	private FotoStorageService fotoStorage;

	@Autowired
	private CatalagoFotoProdutoService catalagoFotoProduto;

	private FotoRecuperada fotoRecuperada;

	@GetMapping
	public ResponseEntity<InputStreamResource> servirFoto(@PathVariable String nomeArquivo,
			@RequestHeader(name = "accept") String acceptHeader)
			throws IOException, HttpMediaTypeNotAcceptableException {

		fotoRecuperada = fotoStorage.recuperar(nomeArquivo);

		try {

			MediaType mediaTypeFoto = retornaTipoDeMediaType(nomeArquivo);
			List<MediaType> mediatypeAceitas = MediaType.parseMediaTypes(acceptHeader);

			verificarCompatibilidadeMediaType(mediaTypeFoto, mediatypeAceitas);

			return ResponseEntity.ok()
					.contentType(mediaTypeFoto)
					.body(new InputStreamResource(fotoRecuperada.getInputStream()));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediatypeAceitas)
			throws HttpMediaTypeNotAcceptableException {

		boolean compativel = mediatypeAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediatypeAceitas);
		}
	}

	private MediaType retornaTipoDeMediaType(String nomeArquivo) {
		FotoProduto fotoProduto = catalagoFotoProduto.buscaFotoPorNome(nomeArquivo);
		String contentType = fotoProduto.getContentType();
		MediaType mediaType = MediaType.parseMediaType(contentType);

		return mediaType;
	}

}
