package com.skcc.reuben;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.skcc.reuben.broadcast.Broadcast;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReubenBusRegistrarTests {
	
	
	@ReubenBus(name = "reubenBus")
	protected interface ReubenBusTest {
		
		@Broadcast(name = "test")
		String reubenTest();

	}

	@Configuration(proxyBeanMethods = false)
	@EnableAutoConfiguration
	@EnableReubenBus
	protected static class ReubenBusTestConfig {

//		private MessageChannel cloudBusOutboundChannel;
//		private ServiceMatcher serviceMatcher;
//		
//		public ReubenBusTestConfig(ServiceMatcher serviceMatcher) {
//			
//			this.serviceMatcher = serviceMatcher;
//		}
//		@Autowired
//		@Output(SpringCloudBusClient.OUTPUT)
//		public void setCloudBusOutboundChannel(MessageChannel cloudBusOutboundChannel) {
//			this.cloudBusOutboundChannel = cloudBusOutboundChannel;
//		}
//		
//		@EventListener(classes = RemoteApplicationEvent.class)
//		public void acceptLocal(RemoteApplicationEvent event) {
//			
//			
//			log.error("event.getOriginService() [{}]", event.getOriginService());
//			log.error("serviceMatcher.getServiceId() [{}]", serviceMatcher.getServiceId());
//			if (this.serviceMatcher.isFromSelf(event)
//					&& !(event instanceof AckRemoteApplicationEvent)) {
//				if (log.isDebugEnabled()) {
//					log.debug("Sending remote event on bus: " + event);
//				}
//				this.cloudBusOutboundChannel.send(MessageBuilder.withPayload(event).build());
//			}
//			
//		}
	}
	
	
	@Test
	public void testDefault() {
		
		ConfigurableApplicationContext context = SpringApplication.run(new Class[] { ReubenAutoConfiguration.class, ReubenBusTestConfig.class, ReubenBusAutoConfiguration.class},
				new String[] { "--spring.main.allow-bean-definition-overriding=true" });
		ReubenBusTest reubenBus = context.getBean(ReubenBusTest.class);
		reubenBus.reubenTest();
		assertTrue(ReubenBusTest.class.isInstance(reubenBus));
	}
		

}