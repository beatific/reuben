package com.skcc.reuben;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ReubenBusRegistrar.class)
public @interface EnableReubenBus {

	String[] value() default {};

	String[] basePackages() default {};

	Class<?>[] basePackageClasses() default {};
	
	Class<?>[] defaultConfiguration() default {};

}

