package com.souza.souzafood.api.model;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.SouzaFoodLinks;
import com.souza.souzafood.api.controller.RestauranteController;
import com.souza.souzafood.domain.model.Restaurante;

@Component
public class RestauranteBasicoModelAssembler 
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private SouzaFoodLinks souzaFoodLinks;
    
    public RestauranteBasicoModelAssembler() {
        super(RestauranteController.class, RestauranteBasicoModel.class);
    }
    
    @Override
    public RestauranteBasicoModel toModel(Restaurante restaurante) {
        RestauranteBasicoModel restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);
        
        modelMapper.map(restaurante, restauranteModel);
        
        restauranteModel.add(souzaFoodLinks.linkToRestaurantes("restaurantes"));
        
        restauranteModel.getCozinha().add(
        		souzaFoodLinks.linkToCozinha(restaurante.getCozinha().getId()));
        
        return restauranteModel;
    }
    
    @Override
    public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(souzaFoodLinks.linkToRestaurantes());
    }   
}        