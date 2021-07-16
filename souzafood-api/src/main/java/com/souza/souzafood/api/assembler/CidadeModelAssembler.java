package com.souza.souzafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.SouzaFoodLinks;
import com.souza.souzafood.api.controller.CidadeController;
import com.souza.souzafood.api.model.CidadeModel;
import com.souza.souzafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends
          RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private SouzaFoodLinks souzaFoodLinks;
	
	public CidadeModelAssembler() {

		super(CidadeController.class, CidadeModel.class);
	}

	@Override
	public CidadeModel toModel(Cidade cidade) {
	    CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
	    
	    modelMapper.map(cidade, cidadeModel);
	    
	    cidadeModel.add(souzaFoodLinks.linkToCidades("cidades"));
	    
	    cidadeModel.getEstado().add(souzaFoodLinks.linkToEstado(cidadeModel.getEstado().getId()));
	    
	    return cidadeModel;
	}

	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
	    return super.toCollectionModel(entities)
	            .add(souzaFoodLinks.linkToCidades());
	}
	
//	public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
//		return cidades.stream()
//				.map(cidade -> toModel(cidade))
//				.collect(Collectors.toList());
//	}
}
