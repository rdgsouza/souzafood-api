package com.souza.souzafood.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.model.UrlFotoProdutoModel;
import com.souza.souzafood.domain.model.FotoProduto;

@Component
public class UrlFotoProdutoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public UrlFotoProdutoModel toModel(FotoProduto foto) {
		return modelMapper.map(foto, UrlFotoProdutoModel.class);
	}

	public List<UrlFotoProdutoModel> toModelList(List<FotoProduto> fotos) {
		return fotos.stream().map(this::toModel).collect(Collectors.toList());
	}
}