package com.souza.souzafood.core.web;

import org.springframework.http.MediaType;

public class SouzaFoodMediaTypes {

	public static final String V1_APPLICATION_JSON_VALUE = "application/vnd.souzafood.v1+json;";

	public static final MediaType V1_APPLICATION_JSON = MediaType.valueOf(V1_APPLICATION_JSON_VALUE);

	public static final String V2_APPLICATION_JSON_VALUE = "application/vnd.souzafood.v2+json;";

	public static final MediaType V2_APPLICATION_JSON = MediaType.valueOf(V2_APPLICATION_JSON_VALUE);
	
}
