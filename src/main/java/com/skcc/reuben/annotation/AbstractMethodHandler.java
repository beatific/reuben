package com.skcc.reuben.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.util.ReflectionUtils;

import com.skcc.reuben.ReubenBus;
import com.skcc.reuben.build.MethodHandler;
import com.skcc.reuben.event.ConvertableRemoteCommunicationEvent;
import com.skcc.reuben.event.RemoteCommunicationEvent;
import com.skcc.reuben.payload.Payload;

public abstract class AbstractMethodHandler implements MethodHandler, ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;
	
	private Map<Method, String> targets = new HashMap<>();;
	
	private AnnotationMapping mapping;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	@Override
	public void setAnnotationMapping(AnnotationMapping mapping) {
		this.mapping = mapping;
	}
	
	protected void publish(RemoteCommunicationEvent event) {
		applicationEventPublisher.publishEvent(event);
	}
	
	protected abstract Class<? extends ConvertableRemoteCommunicationEvent> event();
	
	protected boolean isBroadcast() {
		return false;
	}
	
	private Object param(Annotation annotation, String methodName) {
		try {
			Method method = annotation.getClass().getMethod(methodName, new Class<?>[] {});
			return ReflectionUtils.invokeMethod(method, annotation);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new UnsupportedOperationException("Annotation '" + annotation.getClass().getSimpleName() + "' does not have '" + methodName +"'");
		}
		
	}
	
	private String target(Method method) {
		
		return Optional.ofNullable(targets.get(method)).orElseGet(() -> {
			String name = (String)param(method.getAnnotation(mapping.annotation()), "name");
			targets.put(method, name);
			return name;
		});
	}
	
	@Override
	public Object invoke(ReubenBus reuben, Method method, Object[] args) throws Throwable {
		
		Payload payload = Payload.of(args);
		String originService = null;
		
		String destinationService = isBroadcast() ? null : reuben.name();
		String name = target(method);
		
		RemoteCommunicationEvent remoteCommunicationEvent = new RemoteCommunicationEvent(name, payload, originService, destinationService);
		ConvertableRemoteCommunicationEvent event = ConvertableRemoteCommunicationEvent.convert(event(), remoteCommunicationEvent);
		publish(event);
		
		return null;
	}
	
	
}
