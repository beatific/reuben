package com.skcc.reuben.build;

import java.lang.reflect.InvocationHandler;

public interface InvocationHandlerFactory {

	public InvocationHandler create(MethodHandlerResolver handlerResolver);
}
