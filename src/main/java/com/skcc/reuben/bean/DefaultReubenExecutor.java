package com.skcc.reuben.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultReubenExecutor implements ReubenExecutor {

	@Override
	public void execute(Object object, Object... params) {
		
		Class<?> clazz = object.getClass();
		
		Method[] methods = clazz.getMethods();
		
//		Arrays.asList(methods).stream().filter(m -> m.getAnnotation(Action.class) != null).forEach(m -> ReflectionUtils.invokeMethod(m, params));
		
//		log.error("class name [{}]", clazz.getName());
		List<Method> actionMethods = Arrays.asList(methods).stream().filter(m -> m.getAnnotation(Action.class) != null).collect(Collectors.toList());
		
		Assert.isTrue(actionMethods.size() == 1, "@Action specificated method must be only One! [" + clazz.getSimpleName() + "]");
		Method method = actionMethods.get(0);
		
		Class<?>[] parameterTypes = method.getParameterTypes();
		
		
		for(Object param : params) {
			log.error("param type {}", param.getClass());
		}
//		List<Object> parameters = new ArrayList<>();
//		for(int i =0; i < parameterTypes.length; i++) {
//			parameters.add(parameterTypes[i].cast(params[i]));
//		}
		
//		if(parameters.size() == 0) {
//			ReflectionUtils.invokeMethod(method, object);
//		} else if(parameters.size() == 1) {
			ReflectionUtils.invokeMethod(method, object, params);
//		} else {
//	  	    ReflectionUtils.invokeMethod(method, object, parameters.toArray());
//		}
		
//		methodStream.forEach(m -> ReflectionUtils.invokeMethod(m, params));
		
	}

}
