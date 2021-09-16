//package com.souza.souzafood.core.web;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
////https://app.algaworks.com/aulas/2221/depreciando-uma-versao-da-api
//@Component
//public class ApiRetirementHandler extends HandlerInterceptorAdapter {
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		
//		if(request.getRequestURI().startsWith("/v1/")) {
//			response.addHeader("X-SouzaFood-Deprecated", 
//					"Essa versão da API está depreciada e deixará de existir a partir de 01/01/2022."
//					+ "Use a versão mas atual da API.");
//		}
//		
//		return true;
//	}
//	
//}
