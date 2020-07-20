package com.skcc.reuben;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.cloud.context.named.NamedContextFactory;
import org.springframework.lang.Nullable;

import com.skcc.reuben.ReubenBusConfiguration;
import com.skcc.reuben.ReubenBusSpecification;

public class ReubenContext extends NamedContextFactory<ReubenBusSpecification> {

	private Map<String, Object> reubensOfName = new ConcurrentHashMap<>();
	private Map<Class<?>, Object> reubensOfType = new ConcurrentHashMap<>();
	
	public ReubenContext() {
		super(ReubenBusConfiguration.class, "reuben", "reuben.bus.name");
	}
	
	@Nullable
	public <T> T getInstanceWithoutAncestors(String name, Class<T> type) {
		try {
			return BeanFactoryUtils.beanOfType(getContext(name), type);
		}
		catch (BeansException ex) {
			return null;
		}
	}

	@Nullable
	public <T> Map<String, T> getInstancesWithoutAncestors(String name, Class<T> type) {
		return getContext(name).getBeansOfType(type);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getReuben(String name) {
		return (T)reubensOfName.get(name);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getReuben(Class<?> type) {
		return (T)reubensOfType.get(type);
	}
	
	void registerReuben(String name, Object reuben) {
		reubensOfName.put(name, reuben);
		reubensOfType.put(reuben.getClass(), reuben);
	}

}
