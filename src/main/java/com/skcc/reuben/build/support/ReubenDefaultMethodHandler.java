package com.skcc.reuben.build.support;

import java.lang.reflect.Method;

import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationEventPublisher;

import com.skcc.reuben.ReubenBus;
import com.skcc.reuben.ReubenBusProperties;
import com.skcc.reuben.annotation.AnnotationMapping;
import com.skcc.reuben.build.DefaultMethodHandler;

public class ReubenDefaultMethodHandler implements DefaultMethodHandler {

	@Override
	public Object invoke(ReubenBus reuben, Method method, Object[] args) throws Throwable {
		throw new UnsupportedOperationException("Method '" + method.getName() + "' is not supported");
	}

	@Override
	public void setAnnotationMapping(AnnotationMapping mapping) {
		
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		
	}

	@Override
	public void setReubenBusProperties(ReubenBusProperties properties) {
		
	}

}
