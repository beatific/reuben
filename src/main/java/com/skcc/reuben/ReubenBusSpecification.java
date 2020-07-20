package com.skcc.reuben;

import org.springframework.cloud.context.named.NamedContextFactory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ReubenBusSpecification implements NamedContextFactory.Specification {

	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private Class<?>[] configuration;

	ReubenBusSpecification() {
	}

	ReubenBusSpecification(String name, Class<?>[] configuration) {
		this.name = name;
		this.configuration = configuration;
	}
}
