package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaLinks;
import com.souza.souzafood.api.v1.controller.EstadoController;
import com.souza.souzafood.api.v1.model.EstadoModel;
import com.souza.souzafood.core.security.SouzaSecurity;
import com.souza.souzafood.domain.model.Estado;

@Component
public class EstadoModelAssembler 
         extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SouzaLinks souzaLinks;
	
	@Autowired
	private SouzaSecurity souzaSecurity;    
	
	   public EstadoModelAssembler() {
	        super(EstadoController.class, EstadoModel.class);
	    }
	
	   @Override
	   public EstadoModel toModel(Estado estado) {
	       EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
	       modelMapper.map(estado, estadoModel);
	       
	       if (souzaSecurity.podeConsultarEstados()) {
	           estadoModel.add(souzaLinks.linkToEstados("estados"));
	       }
	       
	       return estadoModel;
	   }

	   @Override
	   public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
	       CollectionModel<EstadoModel> collectionModel = super.toCollectionModel(entities);
	       
	       if (souzaSecurity.podeConsultarEstados()) {
	           collectionModel.add(souzaLinks.linkToEstados());
	       }
	       
	       return collectionModel;
	   }
	
}