package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaLinks;
import com.souza.souzafood.api.v1.controller.RestauranteProdutoFotoController;
import com.souza.souzafood.api.v1.model.FotoProdutoModel;
import com.souza.souzafood.core.security.SouzaSecurity;
import com.souza.souzafood.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler 
        extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private SouzaLinks souzaLinks;
    
    @Autowired
    private SouzaSecurity souzaSecurity;    
    
    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }
    
    @Override
    public FotoProdutoModel toModel(FotoProduto foto) {
        FotoProdutoModel fotoProdutoModel = modelMapper.map(foto, FotoProdutoModel.class);
        
        // Quem pode consultar restaurantes, também pode consultar os produtos e fotos
        if (souzaSecurity.podeConsultarRestaurantes()) {
            fotoProdutoModel.add(souzaLinks.linkToFotoProduto(
                    foto.getRestauranteId(), foto.getProduto().getId()));
            
            fotoProdutoModel.add(souzaLinks.linkToProduto(
                    foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
        }
        
        return fotoProdutoModel;
    }
 
}