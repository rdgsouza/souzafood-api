package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaLinks;
import com.souza.souzafood.api.v1.controller.CozinhaController;
import com.souza.souzafood.api.v1.model.CozinhaModel;
import com.souza.souzafood.core.security.SouzaSecurity;
import com.souza.souzafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends
          RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SouzaLinks souzaLinks;
	
	@Autowired
	private SouzaSecurity souzaSecurity; 
	
	public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModel.class);
	} 
	
	@Override
	public CozinhaModel toModel(Cozinha cozinha) {
	    CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
	    modelMapper.map(cozinha, cozinhaModel);
	    
	    if (souzaSecurity.podeConsultarCozinhas()) {
	        cozinhaModel.add(souzaLinks.linkToCozinhas("cozinhas"));
	    }
	    
	    return cozinhaModel;
	}
	
}
