package com.skcc.reuben.build;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.skcc.reuben.broadcast.Broadcast;
import com.skcc.reuben.broadcast.BroadcastHandler;
import com.skcc.reuben.build.support.ReubenDefaultMethodHandler;

public class MethodHandlerResolverTests {
	
	MethodHandlerResolver handlerResolver;
	
	MethodHandler handler = new BroadcastHandler();

	@Before
	public void testBefore() {
		
		AnnotationMethodMapping mapping = new AnnotationMethodMapping();
		mapping.register(Broadcast.class, handler);
		
		handlerResolver = new MethodHandlerResolver(TestInterface.class, new ReubenDefaultMethodHandler(), mapping);
	}
	
	@Test
	public void testDefaults() throws Exception {
		
		Method method = TestInterface.class.getDeclaredMethod("test", String.class);
		assertEquals(handler, handlerResolver.resolve(method));
	}
	
	interface TestInterface {

		@Broadcast(name = "test")
		Map<String, String> test(String abc);

	}
	
}
