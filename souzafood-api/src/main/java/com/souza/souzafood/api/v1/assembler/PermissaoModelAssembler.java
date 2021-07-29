package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaFoodLinks;
import com.souza.souzafood.api.v1.model.PermissaoModel;
import com.souza.souzafood.domain.model.Permissao;

@Component
public class PermissaoModelAssembler 
        implements RepresentationModelAssembler<Permissao, PermissaoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private SouzaFoodLinks souzaFoodLinks;

    @Override
    public PermissaoModel toModel(Permissao permissao) {
        PermissaoModel permissaoModel = modelMapper.map(permissao, PermissaoModel.class);
        return permissaoModel;
    }
    
    @Override
    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(souzaFoodLinks.linkToPermissoes());
    }   
}
