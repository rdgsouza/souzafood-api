package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaLinks;
import com.souza.souzafood.api.v1.controller.RestauranteController;
import com.souza.souzafood.api.v1.model.RestauranteModel;
import com.souza.souzafood.core.security.SouzaSecurity;
import com.souza.souzafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler 
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private SouzaLinks souzaLinks;
    
    @Autowired
    private SouzaSecurity souzaSecurity;    

    
    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }
    
    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        
        if (souzaSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(souzaLinks.linkToRestaurantes("restaurantes"));
        }
        
        if (souzaSecurity.podeGerenciarCadastroRestaurantes()) {
            if (restaurante.ativacaoPermitida()) {
                restauranteModel.add(
                        souzaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
            }

            if (restaurante.inativacaoPermitida()) {
                restauranteModel.add(
                        souzaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
            }
        }
        
        if (souzaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if (restaurante.aberturaPermitida()) {
                restauranteModel.add(
                        souzaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
            }

            if (restaurante.fechamentoPermitido()) {
                restauranteModel.add(
                        souzaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
            }
        }
        
        if (souzaSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(souzaLinks.linkToProdutos(restaurante.getId(), "produtos"));
        }
        
        if (souzaSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(
                    souzaLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }
        
        if (souzaSecurity.podeConsultarCidades()) {
            if (restauranteModel.getEndereco() != null 
                    && restauranteModel.getEndereco().getCidade() != null) {
                restauranteModel.getEndereco().getCidade().add(
                        souzaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
            }
        }
        
        if (souzaSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(souzaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), 
                    "formas-pagamento"));
        }
        
        if (souzaSecurity.podeGerenciarCadastroRestaurantes()) {
            restauranteModel.add(souzaLinks.linkToRestauranteResponsaveis(restaurante.getId(), 
                    "responsaveis"));
        }
        
        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteModel> collectionModel = super.toCollectionModel(entities);
        
        if (souzaSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(souzaLinks.linkToRestaurantes());
        }
        
        return collectionModel;
    }   
}