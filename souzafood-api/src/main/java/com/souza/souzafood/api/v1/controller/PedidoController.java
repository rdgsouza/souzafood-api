package com.souza.souzafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.souza.souzafood.api.model.input.PedidoInput;
import com.souza.souzafood.api.model.input.PedidoInputDisassembler;
import com.souza.souzafood.api.openapi.controller.PedidoControllerOpenApi;
import com.souza.souzafood.api.v1.assembler.PedidoModelAssembler;
import com.souza.souzafood.api.v1.assembler.PedidoResumoModelAssembler;
import com.souza.souzafood.api.v1.model.PedidoModel;
import com.souza.souzafood.api.v1.model.PedidoResumoModel;
import com.souza.souzafood.core.data.PageWrapper;
import com.souza.souzafood.core.data.PageableTranslator;
import com.souza.souzafood.core.security.CheckSecurity;
import com.souza.souzafood.core.security.SouzaSecurity;
import com.souza.souzafood.domain.exception.EntidadeNaoEncontradaException;
import com.souza.souzafood.domain.exception.NegocioException;
import com.souza.souzafood.domain.filter.PedidoFilter;
import com.souza.souzafood.domain.model.Pedido;
import com.souza.souzafood.domain.model.Usuario;
import com.souza.souzafood.domain.repository.PedidoRepository;
import com.souza.souzafood.domain.service.EmissaoPedidoService;
import com.souza.souzafood.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;

	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;

	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;

	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler; //https://app.algaworks.com/aulas/2171/desafio-adicionando-hypermedia-em-recursos-de-pedidos-paginacao
	
	@Autowired
	private SouzaSecurity souzaSecurity;
	
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//		List<Pedido> pedidos = pedidoRepository.findAll();
//        List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos);
//		
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//       
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());  
//        
//        if(StringUtils.isNotBlank(campos)) {
//        	filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.
//        			filterOutAllExcept(campos.split(",")));
//        }
//        
//        pedidosWrapper.setFilters(filterProvider);
//        
//        return pedidosWrapper;
//	}
	
	@CheckSecurity.Pedidos.PodePesquisar
	@Override
	@GetMapping
	public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, 
	        @PageableDefault(size = 10) Pageable pageable) {
	   Pageable pageableTraduzido = traduzirPageable(pageable);
	    
	    Page<Pedido> pedidosPage = pedidoRepository.findAll(
	            PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);
//	    https://app.algaworks.com/aulas/2172/corrigindo-links-de-paginacao-com-ordenacao
	    pedidosPage = new PageWrapper<>(pedidosPage, pageable);
	    
	    return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
	}

	@CheckSecurity.Pedidos.PodeBuscar	
	@Override
	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

		return pedidoModelAssembler.toModel(pedido);
	}
 
	@CheckSecurity.Pedidos.PodeCriar
	@Override
	@PostMapping
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(souzaSecurity.getUsuarioId());
// Implementação do metodo emitir em https://www.algaworks.com/aulas/2027/desafio-implementando-o-endpoint-de-emissao-de-pedidos
			novoPedido = emissaoPedido.emitir(novoPedido);

			return pedidoModelAssembler.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
// Aula: https://www.algaworks.com/aulas/2043/implementando-um-conversor-de-propriedades-de-ordenacao
	private Pageable traduzirPageable(Pageable apiPageable) {
	
		var mapeamento = ImmutableMap.of(
				"codigo", "codigo",
				"subtotal","subtotal",
				"restaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal"
				);
		
		return PageableTranslator.translate(apiPageable, mapeamento);
	}
}
