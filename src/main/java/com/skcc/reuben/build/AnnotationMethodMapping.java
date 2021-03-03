package com.skcc.reuben.build;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import lombok.extern.slf4j.Slf4j;

public class AnnotationMethodMapping implements ApplicationEventPublisherAware {

	private Map<Class<? extends Annotation>, MethodHandler> methodHandlers = new ConcurrentHashMap<>();
	private ApplicationEventPublisher applicationEventPublisher;
	
	public void register(Class<? extends Annotation> annotationClass, MethodHandler methodHandler) {
		methodHandler.setApplicationEventPublisher(applicationEventPublisher);
		methodHandlers.put(annotationClass, methodHandler);
	}
	
	public Optional<MethodHandler> methodHandler(Annotation annotation) {
		return Optional.ofNullable(methodHandlers.get(annotation.annotationType()));
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
