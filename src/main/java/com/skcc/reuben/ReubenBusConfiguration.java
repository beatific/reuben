package com.skcc.reuben;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.skcc.reuben.build.AnnotationMethodMapping;
import com.skcc.reuben.build.DefaultMethodHandler;
import com.skcc.reuben.build.InvocationHandlerFactory;
import com.skcc.reuben.build.ReubenBuilder;
import com.skcc.reuben.build.support.ReubenDefaultMethodHandler;
import com.skcc.reuben.build.support.ReubenInvocationHandlerFactory;

@Configuration(proxyBeanMethods = false)
public class ReubenBusConfiguration {


	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	public ReubenBuilder reubenBuilder() {
		return new ReubenBuilder();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public InvocationHandlerFactory invocationHandlerFactory() {
		return new ReubenInvocationHandlerFactory();
	}

	@Bean
	@ConditionalOnMissingBean
	public DefaultMethodHandler defaultMethodHandler() {
		return new ReubenDefaultMethodHandler();
	}
	
	@Bean
	public AnnotationMethodMapping annotationMethodMapping() {
		return new AnnotationMethodMapping();
	}
	
}
