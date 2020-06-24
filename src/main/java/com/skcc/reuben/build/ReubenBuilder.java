package com.skcc.reuben.build;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.annotation.Autowired;

public class ReubenBuilder {

	@Autowired
	private InvocationHandlerFactory invocationHandlerFactory;
	@Autowired
	private DefaultMethodHandler defaultMethodHandler;
	@Autowired
	private AnnotationMethodMapping mapping;
	
	public <T> T build(Class<T> clazz) {

		MethodHandlerResolver methodResolver = new MethodHandlerResolver(clazz, defaultMethodHandler, mapping);
		
		InvocationHandler handler = invocationHandlerFactory.create(methodResolver);
		T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, handler);
		
		return proxy;

	}
	
}
