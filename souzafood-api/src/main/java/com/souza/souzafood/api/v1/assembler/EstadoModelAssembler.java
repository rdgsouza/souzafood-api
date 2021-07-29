package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaFoodLinks;
import com.souza.souzafood.api.v1.controller.EstadoController;
import com.souza.souzafood.api.v1.model.EstadoModel;
import com.souza.souzafood.domain.model.Estado;

@Component
public class EstadoModelAssembler 
         extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SouzaFoodLinks souzaFoodLinks;
	
	   public EstadoModelAssembler() {
	        super(EstadoController.class, EstadoModel.class);
	    }
	
	   @Override
	   public EstadoModel toModel(Estado estado) {
	       EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
	       modelMapper.map(estado, estadoModel);
	       
	       estadoModel.add(souzaFoodLinks.linkToEstados("estados"));
	       
	       return estadoModel;
	   }

	   @Override
	   public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
	       return super.toCollectionModel(entities)
	           .add(souzaFoodLinks.linkToEstados());
	   }               
	
}