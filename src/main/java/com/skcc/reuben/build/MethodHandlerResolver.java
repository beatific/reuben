package com.skcc.reuben.build;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MethodHandlerResolver {

	private Map<Method, MethodHandler> handlers = new HashMap<>();
	
	private DefaultMethodHandler defaultMethodHandler;
	private AnnotationMethodMapping mapping;
	
	public MethodHandlerResolver(Class<?> clazz, DefaultMethodHandler defaultMethodHandler, AnnotationMethodMapping mapping) {
		this.defaultMethodHandler  = defaultMethodHandler;
		this.mapping = mapping;
		Arrays.asList(clazz.getMethods()).stream().forEach(m -> {

			Arrays.asList(m.getAnnotations())
					.forEach(a -> handlers.put(m, mapping.methodHandler(a)));

		});
	}

	public MethodHandler resolve(Method method) {
		return Optional.ofNullable(handlers.get(method)).orElse(defaultMethodHandler);
	}
}
