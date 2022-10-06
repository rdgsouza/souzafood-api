package com.souza.souzafood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;

import com.souza.souzafood.domain.service.EnvioEmailService;

import lombok.extern.slf4j.Slf4j;

// Aula: https://app.algaworks.com/aulas/3651/dependencia-de-javamailsender-nao-satisfeita-melhorando-o-uso-da-heranca
@Slf4j
public class FakeEnvioEmailService implements EnvioEmailService {

	@Autowired
	private ProcessadorEmailTemplate processadorEmailTemplate;
	
    @Override
    public void enviar(Mensagem mensagem) {
        // Foi necessário alterar o modificador de acesso do método processarTemplate
        // da classe pai para "protected", para poder chamar aqui
        String corpo = processadorEmailTemplate.processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }
}     