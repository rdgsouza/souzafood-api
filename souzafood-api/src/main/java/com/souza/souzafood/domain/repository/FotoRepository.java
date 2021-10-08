package com.souza.souzafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.souza.souzafood.domain.model.FotoProduto;

@Repository
public interface FotoRepository extends JpaRepository<FotoProduto, Long> {

	Optional<FotoProduto> findFotoProdutoByNomeArquivo(String nomeArquivo);
}
