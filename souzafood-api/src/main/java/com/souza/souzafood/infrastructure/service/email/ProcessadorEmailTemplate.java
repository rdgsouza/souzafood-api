package com.souza.souzafood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.souza.souzafood.domain.service.EnvioEmailService.Mensagem;

import freemarker.template.Configuration;
import freemarker.template.Template;

//https://app.algaworks.com/aulas/3651/dependencia-de-javamailsender-nao-satisfeita-melhorando-o-uso-da-heranca
@Component
public class ProcessadorEmailTemplate {

		@Autowired
		private Configuration freemarkerConfig;
		
		protected String processarTemplate(Mensagem mensagem) {
		
		try {
			Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(
					template, mensagem.getVariaveis());			
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do e-mail", e);
		} 
	}	
	
}
