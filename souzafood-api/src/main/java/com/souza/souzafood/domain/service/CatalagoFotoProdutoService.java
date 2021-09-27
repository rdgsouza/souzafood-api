package com.souza.souzafood.domain.service;

import com.souza.souzafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.souza.souzafood.domain.model.FotoProduto;
import com.souza.souzafood.domain.repository.FotoRepository;
import com.souza.souzafood.domain.repository.ProdutoRepository;
import com.souza.souzafood.domain.service.FotoStorageService.NovaFoto;
import com.souza.souzafood.infrastructure.service.storage.StorageException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CatalagoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoRepository fotoRepository;

	@Autowired
	private FotoStorageService fotoStorage;

	private static final List<MediaType> CONTENT_TYPES_ACEITOS = Arrays.asList(
			MediaType.IMAGE_JPEG,
			MediaType.IMAGE_PNG
	);

	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) throws IOException {

		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String nomeArquivoExistente = null;
		String nomeNovoArquivo = fotoStorage.gerarNovoNomeArquivo(foto.getNomeArquivo());
		
		foto.setNomeArquivo(nomeNovoArquivo);

		byte[] fotoEmBytes = dadosArquivo.readAllBytes();
		InputStream dadosIns = new ByteArrayInputStream(fotoEmBytes);
//		implementação do metodo aplicaExtensaoSeNecessario: https://www.algaworks.com/forum/topicos/84365/duvida-quando-o-arquivo-vai-sem-extensao
		aplicarExtensaoSeNecessario(foto, fotoEmBytes);

		Optional<FotoProduto> fotoExistente = produtoRepository
				.findFotoById(restauranteId, produtoId);

		if (fotoExistente.isPresent()) {
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}
		
		foto = produtoRepository.save(foto);
		produtoRepository.flush();

		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.contentType(foto.getContentType())
				.inputStream(dadosIns)
				.build();

		fotoStorage.substituir(nomeArquivoExistente, novaFoto);

		return foto;
	}

	@Transactional
	public void excluir(Long restauranteId, Long produtoId) {
		FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);
		produtoRepository.delete(fotoProduto);
		produtoRepository.flush();
		fotoStorage.remover(fotoProduto.getNomeArquivo());
	}

	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
	}

	public List<FotoProduto> buscarTodos(Long restauranteId) {
		return produtoRepository.findAllRestauranteById(restauranteId);
	}

	public FotoProduto buscaFotoPorNome(String nomeArquivo) {
	 return fotoRepository.findFotoProdutoByNomeArquivo(nomeArquivo)
				.orElseThrow(() -> new FotoProdutoNaoEncontradaException(nomeArquivo));
	}
	
	private void aplicarExtensaoSeNecessario(FotoProduto foto, byte[] fotoEmBytes) throws IOException {
		String rawMediaType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(fotoEmBytes));
		MediaType mediaType = MediaType.valueOf(rawMediaType);
		
		if (naoPossuiExtensao(foto)) {
			if (contentTypeEhAceito(mediaType)) {
				aplicarExtensao(foto, mediaType);
			} else {
				throw new StorageException(String.format("A foto deve ser um dos tipos %s.",
						StringUtils.join(CONTENT_TYPES_ACEITOS.stream().map(MimeType::toString).toArray(), ", ")
					)
				);
			}
		}
	}

	private void aplicarExtensao(FotoProduto foto, MediaType mediaType) throws IOException {
		String nomeArquivo = fotoStorage.gerarNovoNomeArquivo(foto.getNomeArquivo());
		foto.setNomeArquivo(nomeArquivo.concat("."+mediaType.getSubtype()));
		foto.setContentType(mediaType.toString());
	}

	private boolean contentTypeEhAceito(MediaType mediaType) {
		return CONTENT_TYPES_ACEITOS.contains(mediaType);
	}

	private boolean naoPossuiExtensao(FotoProduto foto) {
		return fotoStorage.pegarExtensaoArquivo(foto.getNomeArquivo()).equals("");
	}
}