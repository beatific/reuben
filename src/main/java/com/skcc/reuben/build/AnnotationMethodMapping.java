package com.skcc.reuben.build;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AnnotationMethodMapping {

	private Map<Class<? extends Annotation>, MethodHandler> methodHandlers = new HashMap<>();
	
	public void register(Class<? extends Annotation> annotationClass, MethodHandler methodHandler) {
		methodHandlers.put(annotationClass, methodHandler);
	}
	
	public Optional<MethodHandler> methodHandler(Annotation annotation) {
		return Optional.ofNullable(methodHandlers.get(annotation.getClass()));
	}
}
