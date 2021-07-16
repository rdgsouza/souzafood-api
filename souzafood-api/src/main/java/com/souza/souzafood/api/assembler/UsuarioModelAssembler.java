package com.souza.souzafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.SouzaFoodLinks;
import com.souza.souzafood.api.controller.UsuarioController;
import com.souza.souzafood.api.model.UsuarioModel;
import com.souza.souzafood.domain.model.Usuario;

// https://app.algaworks.com/aulas/2167/desafio-adicionando-hypermedia-nos-recursos-de-usuarios
@Component
public class UsuarioModelAssembler
        extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private SouzaFoodLinks souzaFoodLinks;
    
    public UsuarioModelAssembler() {
        super(UsuarioController.class, UsuarioModel.class);
    }
    
    @Override
    public UsuarioModel toModel(Usuario usuario) {
        UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModel);
        
        usuarioModel.add(souzaFoodLinks.linkToUsuarios("usuarios"));
        
        usuarioModel.add(souzaFoodLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
        
        return usuarioModel;
    }
    
    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
            .add(souzaFoodLinks.linkToUsuarios());
    }         
}                
