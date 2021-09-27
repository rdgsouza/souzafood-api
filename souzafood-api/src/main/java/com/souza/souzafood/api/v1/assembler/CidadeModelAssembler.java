package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaLinks;
import com.souza.souzafood.api.v1.controller.CidadeController;
import com.souza.souzafood.api.v1.model.CidadeModel;
import com.souza.souzafood.core.security.SouzaSecurity;
import com.souza.souzafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends
          RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private SouzaLinks souzaLinks;
	
	@Autowired
	private SouzaSecurity souzaSecurity;    
	
	public CidadeModelAssembler() {

		super(CidadeController.class, CidadeModel.class);
	}

	@Override
	public CidadeModel toModel(Cidade cidade) {
	    CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
	    
	    modelMapper.map(cidade, cidadeModel);
	    
	    if (souzaSecurity.podeConsultarCidades()) {
	        cidadeModel.add(souzaLinks.linkToCidades("cidades"));
	    }
	    
	    if (souzaSecurity.podeConsultarEstados()) {
	        cidadeModel.getEstado().add(souzaLinks.linkToEstado(cidadeModel.getEstado().getId()));
	    }
	    
	    return cidadeModel;
	}

	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
	    CollectionModel<CidadeModel> collectionModel = super.toCollectionModel(entities);
	    
	    if (souzaSecurity.podeConsultarCidades()) {
	        collectionModel.add(souzaLinks.linkToCidades());
	    }
	    
	    return collectionModel;
	}
	
//	public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
//		return cidades.stream()
//				.map(cidade -> toModel(cidade))
//				.collect(Collectors.toList());
//	}
}
