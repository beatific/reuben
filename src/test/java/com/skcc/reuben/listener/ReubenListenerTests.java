package com.skcc.reuben.listener;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.skcc.reuben.EnableReuben;
import com.skcc.reuben.EnableReubenBus;
import com.skcc.reuben.ReubenAutoConfiguration;
import com.skcc.reuben.ReubenBus;
import com.skcc.reuben.ReubenBusAutoConfiguration;
import com.skcc.reuben.ReubenTest;
import com.skcc.reuben.broadcast.Broadcast;
import com.skcc.reuben.broadcast.BroadcastEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReubenListenerTests {

	private static BroadcastEvent event = null;

	@ReubenBus(name = "application")
	protected interface ReubenBusTest {

		@Broadcast(name = "test")
		String reubenTest(String test);

	}

	@Configuration(proxyBeanMethods = false)
	@EnableAutoConfiguration
	@EnableReubenBus
	@EnableReuben("com.skcc.reuben")
	protected static class ReubenBusTestConfig  {
		
	}

	@Test
	public void testDefault() {
		
		
		ConfigurableApplicationContext context = SpringApplication.run(
				new Class[] { ReubenAutoConfiguration.class, ReubenBusTestConfig.class,
						ReubenBusAutoConfiguration.class/* , ReubenBusJacksonAutoConfiguration.class */ },
				new String[] { "--spring.main.allow-bean-definition-overriding=true" });
		ReubenBusTest reubenBus = context.getBean(ReubenBusTest.class);
		reubenBus.reubenTest("My name is beatific");
		
		
		ReubenTest reuben = context.getBean(ReubenTest.class);
		while (true) {
			if (reuben.getTest() != null) {
				
				assertEquals("My name is beatific", reuben.getTest());
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
