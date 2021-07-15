package com.souza.souzafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.SouzaFoodLinks;
import com.souza.souzafood.api.controller.CozinhaController;
import com.souza.souzafood.api.model.CozinhaModel;
import com.souza.souzafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends
          RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SouzaFoodLinks souzaFoodLinks;
	
	public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModel.class);
	} 
	
	@Override
	public CozinhaModel toModel(Cozinha cozinha) {
	    CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
	    modelMapper.map(cozinha, cozinhaModel);
	    
	    cozinhaModel.add(souzaFoodLinks.linkToCozinhas("cozinhas"));
	    
	    return cozinhaModel;
	}
	
}
