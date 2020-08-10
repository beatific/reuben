package com.skcc.reuben.build;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.skcc.reuben.broadcast.Broadcast;
import com.skcc.reuben.broadcast.BroadcastHandler;

public class AnnotationMethodMappingTests {

	AnnotationMethodMapping mapping;
	MethodHandler handler = new BroadcastHandler();
	
	@Before
	public void testBefore() {
		mapping = new AnnotationMethodMapping();
		mapping.register(Broadcast.class, handler);
	}
	
	
	@Test
	public void testDefault() throws NoSuchMethodException, SecurityException {
		
		Method method = TestInterface.class.getDeclaredMethod("test", String.class);
		Broadcast broadcast = method.getAnnotation(Broadcast.class);
		
		assertEquals(handler, mapping.methodHandler(broadcast).get());
	}
	
	interface TestInterface {

		@Broadcast(name = "test")
		Map<String, String> test(String abc);

	}
}
