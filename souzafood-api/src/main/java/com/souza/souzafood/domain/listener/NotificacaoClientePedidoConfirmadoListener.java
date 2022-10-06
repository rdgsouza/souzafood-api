package com.souza.souzafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.souza.souzafood.domain.event.PedidoConfirmadoEvent;
import com.souza.souzafood.domain.model.Pedido;
import com.souza.souzafood.domain.service.EnvioEmailService;
import com.souza.souzafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

	@Autowired
	private EnvioEmailService envioEmail;
	
//	https://www.algaworks.com/aulas/2092/reagindo-a-domain-events-em-fases-especificas-da-transacao
	@TransactionalEventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
//      Caso precise que a propiedade souzafood.email.remetente= do application.properties fique dinamica
//	    Implementação feita no forum e suporte https://www.algaworks.com/forum/topicos/84402/duvida-na-propiedade-remetente#90859	
//		String remetente = String.format("%s %s", "SouzaFood", "<souzaafood@gmail.com>");
//		emailProperties.setRemetente(remetente);
		Pedido pedido = event.getPedido();
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("emails/pedido-confirmado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();
		
		envioEmail.enviar(mensagem);
		
	}
}
