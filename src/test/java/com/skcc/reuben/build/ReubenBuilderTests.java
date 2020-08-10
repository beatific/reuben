package com.skcc.reuben.build;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skcc.reuben.ReubenBus;
import com.skcc.reuben.broadcast.Broadcast;
import com.skcc.reuben.broadcast.BroadcastHandler;
import com.skcc.reuben.broadcast.BroadcastMapping;
import com.skcc.reuben.build.support.ReubenDefaultMethodHandler;
import com.skcc.reuben.build.support.ReubenInvocationHandlerFactory;
import com.skcc.reuben.event.RemoteCommunicationEvent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ReubenBuilderTests {

	ReubenBuilder builder = new ReubenBuilder();
	
	@Autowired
	ApplicationEventPublisher applicationEventPublisher;
	
	@Before
	public void testBefore() {
		builder.setDefaultMethodHandler(new ReubenDefaultMethodHandler());
		builder.setInvocationHandlerFactory(new ReubenInvocationHandlerFactory());
		
		AnnotationMethodMapping mapping = new AnnotationMethodMapping();
		mapping.setApplicationEventPublisher(applicationEventPublisher);
		
	    MethodHandler handler = new BroadcastHandler();
	    handler.setAnnotationMapping(new BroadcastMapping());
	    
		mapping.register(Broadcast.class, handler);
		
		builder.setMapping(mapping);
	}
	
	@Test
	public void testDefaults() throws Exception {
		
		TestInterface test = builder.build(TestInterface.class);
		test.test("test");
	}
	
	@EventListener(classes = RemoteCommunicationEvent.class)
	public void acceptLocal(RemoteCommunicationEvent event) {
		
		String actual = (String)event.getSource();
		assertEquals("success", "test", actual);
		
	}

	@ReubenBus(name="reuben")
	interface TestInterface {

		@Broadcast(name = "test")
		Map<String, String> test(String abc);

	}
}
