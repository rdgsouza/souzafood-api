package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaFoodLinks;
import com.souza.souzafood.api.v1.controller.RestauranteController;
import com.souza.souzafood.api.v1.model.RestauranteModel;
import com.souza.souzafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler 
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private SouzaFoodLinks souzaFoodLinks;
    
    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }
    
    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        
        restauranteModel.add(souzaFoodLinks.linkToRestaurantes("restaurantes"));
        
        if (restaurante.ativacaoPermitida()) {
            restauranteModel.add(
            		souzaFoodLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
        }

        if (restaurante.inativacaoPermitida()) {
            restauranteModel.add(
            		souzaFoodLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
        }

        if (restaurante.aberturaPermitida()) {
            restauranteModel.add(
            		souzaFoodLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
        }

        if (restaurante.fechamentoPermitido()) {
            restauranteModel.add(
            		souzaFoodLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
        }
        
        restauranteModel.add(souzaFoodLinks.linkToProdutos(restaurante.getId(), "produtos"));
        
        restauranteModel.getCozinha().add(
        		souzaFoodLinks.linkToCozinha(restaurante.getCozinha().getId()));
        
        if (restauranteModel.getEndereco() != null 
                && restauranteModel.getEndereco().getCidade() != null) {
            restauranteModel.getEndereco().getCidade().add(
            		souzaFoodLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
        }
        
        restauranteModel.add(souzaFoodLinks.linkToRestauranteFormasPagamento(restaurante.getId(), 
                "formas-pagamento"));
        
        restauranteModel.add(souzaFoodLinks.linkToRestauranteResponsaveis(restaurante.getId(), 
                "responsaveis"));
        
        return restauranteModel;
    }
    
    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(souzaFoodLinks.linkToRestaurantes());
    }   
}