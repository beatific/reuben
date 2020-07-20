package com.skcc.reuben.build.support;

import java.lang.reflect.Method;

import com.skcc.reuben.ReubenBus;
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

}
