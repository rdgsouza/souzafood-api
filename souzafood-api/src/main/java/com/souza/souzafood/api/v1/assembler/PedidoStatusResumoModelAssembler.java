package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.model.PedidoStatusResumoModel;
import com.souza.souzafood.domain.model.Pedido;

@Component
public class PedidoStatusResumoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoStatusResumoModel toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoStatusResumoModel.class);
	}
}
