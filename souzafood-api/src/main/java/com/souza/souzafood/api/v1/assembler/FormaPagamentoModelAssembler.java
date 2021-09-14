package com.souza.souzafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.souza.souzafood.api.v1.SouzaLinks;
import com.souza.souzafood.api.v1.controller.FormaPagamentoController;
import com.souza.souzafood.api.v1.model.FormaPagamentoModel;
import com.souza.souzafood.core.security.SouzaSecurity;
import com.souza.souzafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler 
		extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SouzaLinks souzaLinks;
	
	@Autowired
	private SouzaSecurity souzaSecurity;    
	
	public FormaPagamentoModelAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoModel.class);
	}
	
	@Override
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
	    FormaPagamentoModel formaPagamentoModel = 
	            createModelWithId(formaPagamento.getId(), formaPagamento);
	    
	    modelMapper.map(formaPagamento, formaPagamentoModel);
	    
	    if (souzaSecurity.podeConsultarFormasPagamento()) {
	        formaPagamentoModel.add(souzaLinks.linkToFormasPagamento("formasPagamento"));
	    }
	    
	    return formaPagamentoModel;
	}

	@Override
	public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
	    CollectionModel<FormaPagamentoModel> collectionModel = super.toCollectionModel(entities);
	    
	    if (souzaSecurity.podeConsultarFormasPagamento()) {
	        collectionModel.add(souzaLinks.linkToFormasPagamento());
	    }
	        
	    return collectionModel;
	}
	
}