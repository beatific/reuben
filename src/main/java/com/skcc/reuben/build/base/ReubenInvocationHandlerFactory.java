package com.skcc.reuben.build.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.skcc.reuben.build.InvocationHandlerFactory;
import com.skcc.reuben.build.MethodHandlerResolver;

public class ReubenInvocationHandlerFactory implements InvocationHandlerFactory {

	@Override
	public InvocationHandler create(MethodHandlerResolver handlerResolver) {
		return new ReubenInvocationHandler(handlerResolver);
	}
	
    class ReubenInvocationHandler implements InvocationHandler {
		
		private MethodHandlerResolver handlerResolver;
		
		ReubenInvocationHandler(MethodHandlerResolver handlerResolver) {
			this.handlerResolver = handlerResolver;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			
			return handlerResolver.resolve(method).invoke(args);
			
		}

	}
}
