package com.skcc.reuben.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.ReflectionUtils;

import com.skcc.reuben.ReubenBus;
import com.skcc.reuben.ReubenBusProperties;
import com.skcc.reuben.build.MethodHandler;
import com.skcc.reuben.event.ConvertableRemoteCommunicationEvent;
import com.skcc.reuben.event.RemoteCommunicationEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractMethodHandler implements MethodHandler {

	private ApplicationEventPublisher applicationEventPublisher;
	
	private Map<Method, String> targets = new HashMap<>();;
	
	private AnnotationMapping mapping;
	
	private ReubenBusProperties properties;
	
	public void setReubenBusProperties (ReubenBusProperties properties) {
		this.properties = properties;
	}
	
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
	
	protected boolean isBroadcast(Method method) {
		return false;
	}
	
	private Object param(Annotation annotation, String methodName) {
		try {
			Method method = annotation.annotationType().getMethod(methodName, new Class<?>[] {});
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
		
		List<?> payload = Arrays.asList(Optional.ofNullable(args).orElse(new Object[0]));
		
		log.error("payload [{}]", payload);
		String originService = properties.getId();
		
		String destinationService = isBroadcast(method) ? null : reuben.name();
		String name = target(method);
		
		RemoteCommunicationEvent remoteCommunicationEvent = new RemoteCommunicationEvent(name, payload, originService, destinationService);
		ConvertableRemoteCommunicationEvent event = ConvertableRemoteCommunicationEvent.convert(event(), remoteCommunicationEvent);
		
		log.error("ConvertableRemoteCommunicationEvent [{}]", event);
		publish(event);
		
		return null;
	}
	
	
}
