package com.skcc.reuben;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import(ReubenRegistrar.class)
public class ReubenAutoConfiguration {

	@Autowired(required = false)
	private List<ReubenSpecification> configurations = new ArrayList<>();
	
	@Bean
	public ReubenContext reubenContext() {
		ReubenContext context = new ReubenContext();
		context.setConfigurations(this.configurations);
		return context;
	}
}
