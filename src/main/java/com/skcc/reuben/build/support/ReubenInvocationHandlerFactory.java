package com.skcc.reuben.build.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.skcc.reuben.ReubenBus;
import com.skcc.reuben.build.InvocationHandlerFactory;
import com.skcc.reuben.build.MethodHandlerResolver;

public class ReubenInvocationHandlerFactory implements InvocationHandlerFactory {

	@Override
	public InvocationHandler create(MethodHandlerResolver handlerResolver) {
		return new ReubenInvocationHandler(handlerResolver);
	}
	
    class ReubenInvocationHandler implements InvocationHandler {
		
		private final MethodHandlerResolver handlerResolver;
		private final ReubenBus reuben;
		
		ReubenInvocationHandler(MethodHandlerResolver handlerResolver) {
			this.handlerResolver = handlerResolver;
			reuben = handlerResolver.getClazz().getAnnotation(ReubenBus.class);
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			
			return handlerResolver.resolve(method).invoke(reuben, method, args);
			
		}

	}
}
