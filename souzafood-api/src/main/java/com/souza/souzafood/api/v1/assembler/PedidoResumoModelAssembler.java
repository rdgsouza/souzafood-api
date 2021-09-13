package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaLinks;
import com.souza.souzafood.api.v1.controller.PedidoController;
import com.souza.souzafood.api.v1.model.PedidoResumoModel;
import com.souza.souzafood.core.security.SouzaSecurity;
import com.souza.souzafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler 
        extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SouzaLinks souzaLinks;
    
    @Autowired
    private SouzaSecurity souzaSecurity;  
    
    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }
    
    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        if (souzaSecurity.podePesquisarPedidos()) {
            pedidoModel.add(souzaLinks.linkToPedidos("pedidos"));
        }
        
        if (souzaSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(
                    souzaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if (souzaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(souzaLinks.linkToUsuario(pedido.getCliente().getId()));
        }
        
        return pedidoModel;
    }
}
