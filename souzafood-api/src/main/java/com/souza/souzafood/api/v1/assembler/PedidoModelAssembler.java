	package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaLinks;
import com.souza.souzafood.api.v1.controller.PedidoController;
import com.souza.souzafood.api.v1.model.PedidoModel;
import com.souza.souzafood.core.security.SouzaSecurity;
import com.souza.souzafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler 
        extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SouzaLinks souzaLinks;
    
    @Autowired
    private SouzaSecurity souzaSecurity;
        
    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }
    
    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
// https://app.algaworks.com/aulas/2294/desafio-gerando-links-do-hal-dinamicamente-de-acordo-com-permissoes
        // Não usei o método algaSecurity.podePesquisarPedidos(clienteId, restauranteId) aqui,
        // porque na geração do link, não temos o id do cliente e do restaurante, 
        // então precisamos saber apenas se a requisição está autenticada e tem o escopo de leitura
        if (souzaSecurity.podePesquisarPedidos()) {
            pedidoModel.add(souzaLinks.linkToPedidos("pedidos"));
        }
        
        if (souzaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            if (pedido.podeSerConfirmado()) {
                pedidoModel.add(souzaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
            }
            
            if (pedido.podeSerCancelado()) {
                pedidoModel.add(souzaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
            }
            
            if (pedido.podeSerEntregue()) {
                pedidoModel.add(souzaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
            }
        }
        
        if (souzaSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(
                    souzaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }
        
        if (souzaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(
                    souzaLinks.linkToUsuario(pedido.getCliente().getId()));
        }
        
        if (souzaSecurity.podeConsultarFormasPagamento()) {
            pedidoModel.getFormaPagamento().add(
                    souzaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        }
        
        if (souzaSecurity.podeConsultarCidades()) {
            pedidoModel.getEnderecoEntrega().getCidade().add(
                    souzaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        }
        
        // Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
        if (souzaSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getItens().forEach(item -> {
                item.add(souzaLinks.linkToProduto(
                        pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
            });
        }
        
        return pedidoModel;
    }
}

