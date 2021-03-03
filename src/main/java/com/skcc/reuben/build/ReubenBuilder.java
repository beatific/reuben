package com.skcc.reuben.build;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import lombok.Setter;

public class ReubenBuilder {

	@Setter
	private InvocationHandlerFactory invocationHandlerFactory;
	@Setter
	private DefaultMethodHandler defaultMethodHandler;
	@Setter
	private AnnotationMethodMapping mapping;
	
	@SuppressWarnings("unchecked")
	public <T> T build(Class<T> clazz) {

		MethodHandlerResolver methodResolver = new MethodHandlerResolver(clazz, defaultMethodHandler, mapping);
		
		InvocationHandler handler = invocationHandlerFactory.create(methodResolver);
		T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, handler);
		
		return proxy;

	}

}
