package com.skcc.reuben.build;

import java.lang.reflect.Method;

import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationEventPublisherAware;

import com.skcc.reuben.ReubenBus;
import com.skcc.reuben.ReubenBusProperties;
import com.skcc.reuben.annotation.AnnotationMapping;

public interface MethodHandler extends ApplicationEventPublisherAware{

	Object invoke(ReubenBus reuben, Method method, Object[] args) throws Throwable;
	
	public void setAnnotationMapping(AnnotationMapping mapping);
	
	public void setReubenBusProperties(ReubenBusProperties properties);
	
}