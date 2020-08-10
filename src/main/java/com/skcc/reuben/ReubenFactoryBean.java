package com.skcc.reuben;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReubenFactoryBean implements FactoryBean<Object>, InitializingBean, ApplicationContextAware {

	@Getter @Setter
	private Class<?> type;
	
	@Getter @Setter
	private String contextId;
	
	@Getter @Setter
	private boolean inheritParentContext = true;
	
//	private ReubenContext reubenContext;
	private ApplicationContext applicationContext;
	
	@Override
	public Object getObject() throws Exception {
		
//		Object bean = reubenContext.getInstanceWithoutAncestors(contextId, type);
		
//		Object bean = applicationContext.getBean(type);
		
//		ReubenContext reubenContext = this.applicationContext.getBean(ReubenContext.class);
//		
//		Object bean = reubenContext.getReuben(type);
//		Object bean = applicationContext.getBean(type);
		
//		if(bean == null) {
		    Object bean = BeanUtils.instantiateClass(type);
//		    reubenContext.registerReuben(contextId, bean);
//		}
		
		return bean;
	}
	
	@Override
	public Class<?> getObjectType() {
		return this.type;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(this.contextId, "contextId must be set");
	}


}