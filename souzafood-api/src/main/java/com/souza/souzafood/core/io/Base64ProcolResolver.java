package com.souza.souzafood.core.io;

import java.util.Base64;

import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class Base64ProcolResolver implements ProtocolResolver, 
        ApplicationListener<ApplicationContextInitializedEvent> {

//	https://app.algaworks.com/aulas/3627/externalizando-o-keystore-criando-um-protocolresolver-para-base64
	@Override
	public Resource resolve(String location, ResourceLoader resourceLoader) {
		if (location.startsWith("base64:")) {
			byte[] decodedResource = Base64.getDecoder().decode(location.substring(7));

			return new ByteArrayResource(decodedResource);
		}
		return null;
	}

//	https://app.algaworks.com/aulas/3627/externalizando-o-keystore-criando-um-protocolresolver-para-base64
	@Override
	public void onApplicationEvent(ApplicationContextInitializedEvent event) {
        event.getApplicationContext().addProtocolResolver(this);
	}

}
