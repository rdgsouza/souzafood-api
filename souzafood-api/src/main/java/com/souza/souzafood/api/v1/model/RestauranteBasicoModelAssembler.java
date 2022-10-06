package com.souza.souzafood.api.v1.model;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaLinks;
import com.souza.souzafood.api.v1.controller.RestauranteController;
import com.souza.souzafood.core.security.SouzaSecurity;
import com.souza.souzafood.domain.model.Restaurante;

@Component
public class RestauranteBasicoModelAssembler 
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private SouzaLinks souzaLinks;
    
    @Autowired
    private SouzaSecurity souzaSecurity;
    
    public RestauranteBasicoModelAssembler() {
        super(RestauranteController.class, RestauranteBasicoModel.class);
    }
    
    @Override
    public RestauranteBasicoModel toModel(Restaurante restaurante) {
        RestauranteBasicoModel restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);
        
        modelMapper.map(restaurante, restauranteModel);
        
        if (souzaSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(souzaLinks.linkToRestaurantes("restaurantes"));
        }
        
        if (souzaSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(
                    souzaLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }
        
        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteBasicoModel> collectionModel = super.toCollectionModel(entities);
        
        if (souzaSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(souzaLinks.linkToRestaurantes());
        }
                
        return collectionModel;
    }   
}        