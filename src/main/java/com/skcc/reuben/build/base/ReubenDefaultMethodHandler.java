package com.skcc.reuben.build.base;

import com.skcc.reuben.build.MethodHandler;

public class ReubenDefaultMethodHandler implements MethodHandler {

	@Override
	public Object invoke(Object[] argv) throws Throwable {
		
		throw new NotSupportedMethodException("This Method is not supported");
	}

}
