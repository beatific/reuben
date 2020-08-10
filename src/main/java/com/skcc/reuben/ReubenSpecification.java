package com.skcc.reuben;

import java.util.Set;

import org.springframework.cloud.context.named.NamedContextFactory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ReubenSpecification implements NamedContextFactory.Specification {

	@Getter @Setter
	private String name;
//	
//	@Getter @Setter
//	private Set<String> basePackages;
	
	@Getter @Setter
	private Class<?>[] configuration;

	ReubenSpecification() {
	}

	ReubenSpecification(String name, Class<?>[] configuration) {
		this.name = name;
		this.configuration = configuration;
	}
}
