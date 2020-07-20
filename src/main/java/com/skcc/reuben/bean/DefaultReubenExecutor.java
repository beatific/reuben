package com.skcc.reuben.bean;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

public class DefaultReubenExecutor implements ReubenExecutor {

	public void execute(Object object, Object... params) {
		
		Class<?> clazz = object.getClass();
		Method[] methods = clazz.getMethods();
		Stream<Method> methodStream = Arrays.asList(methods).stream().filter(m -> m.getAnnotation(Action.class) != null);
		
		Assert.isTrue(methodStream.count() == 1, "@Action specificated method must be only One! [" + clazz.getSimpleName() + "]");
		
		methodStream.forEach(m -> ReflectionUtils.invokeMethod(m, params));
		
	}
}
