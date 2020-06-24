package com.skcc.reuben.build;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public class AnnotationMethodMapping {

	private Map<Annotation, MethodHandler> methodHandlers = new HashMap<>();
	
	@Autowired
	private DefaultMethodHandler defaultMethodHandler;
	
	public void register(Annotation annotation, MethodHandler methodHandler) {
		methodHandlers.put(annotation, methodHandler);
	}
	
	public MethodHandler methodHandler(Annotation annotation) {
		return Optional.ofNullable(methodHandlers.get(annotation)).orElse(defaultMethodHandler);
	}
}
