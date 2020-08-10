package com.skcc.reuben.build;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;

public class MethodHandlerResolver {

	private Map<Method, MethodHandler> handlers = new HashMap<>();
	
	private DefaultMethodHandler defaultMethodHandler;
	
	@Getter
	private final Class<?> clazz;
	
	public MethodHandlerResolver(Class<?> clazz, DefaultMethodHandler defaultMethodHandler, AnnotationMethodMapping mapping) {
		this.defaultMethodHandler  = defaultMethodHandler;
		this.clazz = clazz;
		Arrays.asList(clazz.getDeclaredMethods()).stream().forEach(m -> {

			Arrays.asList(m.getAnnotations())
					.forEach(a -> handlers.put(m, mapping.methodHandler(a).orElse(defaultMethodHandler)));

		});
	}

	public MethodHandler resolve(Method method) {
		return Optional.ofNullable(handlers.get(method)).orElse(defaultMethodHandler);
	}
}
