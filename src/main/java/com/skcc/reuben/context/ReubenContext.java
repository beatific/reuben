package com.skcc.reuben.context;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.cloud.context.named.NamedContextFactory;
import org.springframework.lang.Nullable;

import com.skcc.reuben.ReubenBusConfiguration;
import com.skcc.reuben.ReubenBusSpecification;

public class ReubenContext extends NamedContextFactory<ReubenBusSpecification> {

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

}
