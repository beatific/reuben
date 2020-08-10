package com.skcc.reuben.build.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.util.Assert;

import com.skcc.reuben.ReubenBus;
import com.skcc.reuben.build.InvocationHandlerFactory;
import com.skcc.reuben.build.MethodHandlerResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReubenInvocationHandlerFactory implements InvocationHandlerFactory {

	@Override
	public InvocationHandler create(MethodHandlerResolver handlerResolver) {
		return new ReubenInvocationHandler(handlerResolver);
	}
	
    class ReubenInvocationHandler implements InvocationHandler {
		
		private final MethodHandlerResolver handlerResolver;
		private final ReubenBus reubenBus;
		
		ReubenInvocationHandler(MethodHandlerResolver handlerResolver) {
			this.handlerResolver = handlerResolver;
			reubenBus = handlerResolver.getClazz().getAnnotation(ReubenBus.class);
			Assert.notNull(reubenBus, "@RebuenBus must be specified");
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			
			return handlerResolver.resolve(method).invoke(reubenBus, method, args);
			
		}

	}
}
