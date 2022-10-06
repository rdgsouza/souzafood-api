package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaLinks;
import com.souza.souzafood.api.v1.controller.GrupoController;
import com.souza.souzafood.api.v1.model.GrupoModel;
import com.souza.souzafood.core.security.SouzaSecurity;
import com.souza.souzafood.domain.model.Grupo;

@Component
public class GrupoModelAssembler 
        extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private SouzaLinks souzaLinks;
    
    @Autowired
    private SouzaSecurity souzaSecurity;
    
    
    public GrupoModelAssembler() {
        super(GrupoController.class, GrupoModel.class);
    }
    
    @Override
    public GrupoModel toModel(Grupo grupo) {
        GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);
        
        if (souzaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            grupoModel.add(souzaLinks.linkToGrupos("grupos"));
            
            grupoModel.add(souzaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        }
        
        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        CollectionModel<GrupoModel> collectionModel = super.toCollectionModel(entities);
        
        if (souzaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(souzaLinks.linkToGrupos());
        }
        
        return collectionModel;
    }            
}        