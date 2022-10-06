package com.souza.souzafood.domain.service;

import java.util.List;

import com.souza.souzafood.domain.filter.VendaDiariaFilter;
import com.souza.souzafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
