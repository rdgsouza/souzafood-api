package com.souza.souzafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v2.SouzaFoodLinksV2;
import com.souza.souzafood.api.v2.controller.CidadeControllerV2;
import com.souza.souzafood.api.v2.model.CidadeModelV2;
import com.souza.souzafood.domain.model.Cidade;

@Component
public class CidadeModelAssemblerV2 extends
          RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private SouzaFoodLinksV2 souzaFoodLinks;
	
	public CidadeModelAssemblerV2() {
		super(CidadeControllerV2.class, CidadeModelV2.class);
	}

	@Override
	public CidadeModelV2 toModel(Cidade cidade) {
	    CidadeModelV2 cidadeModel = createModelWithId(cidade.getId(), cidade);
	    
	    modelMapper.map(cidade, cidadeModel);
	    
	    cidadeModel.add(souzaFoodLinks.linkToCidades("cidades"));
	   	    
	    return cidadeModel;
	}

	@Override
	public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
	    return super.toCollectionModel(entities)
	            .add(souzaFoodLinks.linkToCidades());
	}
	
//	public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
//		return cidades.stream()
//				.map(cidade -> toModel(cidade))
//				.collect(Collectors.toList());
//	}
}
