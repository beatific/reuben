package com.skcc.reuben;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.skcc.reuben.bean.Reuben;

import lombok.Getter;
import lombok.Setter;

public class ReubenFactoryBean implements FactoryBean<Object>, ApplicationContextAware {

	@Getter @Setter
	private Class<?> type;
	
	private ApplicationContext applicationContext;
	private ReubenContext reubenContext;
	
	@Override
	public Object getObject() throws Exception {
		Object bean = this.applicationContext.getBean(this.type);
		
		Reuben rebuen = this.type.getAnnotation(Reuben.class);
		
		if(bean == null) {
			bean = this.type.newInstance();
		}
		
		reubenContext.registerReuben(rebuen.name(), bean);
		
		return bean;
	}

	@Override
	public Class<?> getObjectType() {
		return this.type;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		this.reubenContext = applicationContext.getBean(ReubenContext.class);
	}


}