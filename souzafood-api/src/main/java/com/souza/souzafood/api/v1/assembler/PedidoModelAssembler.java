	package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaFoodLinks;
import com.souza.souzafood.api.v1.controller.PedidoController;
import com.souza.souzafood.api.v1.model.PedidoModel;
import com.souza.souzafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler 
        extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SouzaFoodLinks souzaFoodLinks;
    
    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }
    
    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        pedidoModel.add(souzaFoodLinks.linkToPedidos("pedidos"));
        
        if(pedido.podeSerConfirmado()) {
        	pedidoModel.add(souzaFoodLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
        }
        
        if(pedido.podeSerCancelado()) {
            pedidoModel.add(souzaFoodLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
        }
        
        if(pedido.podeSerEntregue()) {
            pedidoModel.add(souzaFoodLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
        }
        
        pedidoModel.getRestaurante().add(
        		souzaFoodLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        
        pedidoModel.getCliente().add(
        		souzaFoodLinks.linkToUsuario(pedido.getCliente().getId()));
        
        pedidoModel.getFormaPagamento().add(
        		souzaFoodLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        
        pedidoModel.getEnderecoEntrega().getCidade().add(
        		souzaFoodLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        
        pedidoModel.getItens().forEach(item -> {
            item.add(souzaFoodLinks.linkToProduto(
                    pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });
        
        return pedidoModel;
    }
}