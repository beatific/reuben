package com.skcc.reuben;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReubenBus {

	@AliasFor("name")
	String value() default "";

	String contextId() default "";

	@AliasFor("value")
	String name() default "";

	String qualifier() default "";

	String url() default "";

	Class<?>[] configuration() default {};
}
