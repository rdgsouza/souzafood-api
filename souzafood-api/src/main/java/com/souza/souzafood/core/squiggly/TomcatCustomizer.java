package com.souza.souzafood.core.squiggly;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

//Referências:
//- https://stackoverflow.com/a/53613678
//- https://tomcat.apache.org/tomcat-8.5-doc/config/http.html
//- https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto-configure-webserver

@Component
public class TomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
	
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(connector -> connector.setAttribute("relaxedQueryChars", "[]"));
    }
 //Aula que fala da implementação dessa classe: https://www.algaworks.com/aulas/2035/limitando-os-campos-retornados-pela-api-com-squiggly
    
}
