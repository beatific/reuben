package com.skcc.reuben.build;

import java.lang.reflect.Method;

import com.skcc.reuben.ReubenBus;
import com.skcc.reuben.annotation.AnnotationMapping;

public interface MethodHandler {

	Object invoke(ReubenBus reuben, Method method, Object[] args) throws Throwable;
	
	public void setAnnotationMapping(AnnotationMapping mapping);
	
}