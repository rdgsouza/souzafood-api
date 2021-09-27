package com.souza.souzafood.domain.repository;

import com.souza.souzafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

	FotoProduto save(FotoProduto foto);

	void delete(FotoProduto foto);
}
