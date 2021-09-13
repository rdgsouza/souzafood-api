package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaLinks;
import com.souza.souzafood.api.v1.controller.RestauranteProdutoController;
import com.souza.souzafood.api.v1.model.ProdutoModel;
import com.souza.souzafood.core.security.SouzaSecurity;
import com.souza.souzafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler 
        extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private SouzaLinks souzaFoodLinks;
    
    @Autowired
    private SouzaSecurity souzaSecurity;
    
    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }
    
    @Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());
        
        modelMapper.map(produto, produtoModel);
        
        if (souzaSecurity.podeConsultarRestaurantes()) {
        	produtoModel.add(souzaFoodLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

        	produtoModel.add(souzaFoodLinks.linkToFotoProduto(
        			produto.getRestaurante().getId(), produto.getId(), "foto"));
        }
        
        return produtoModel;
    }          

 }