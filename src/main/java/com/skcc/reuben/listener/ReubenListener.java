package com.skcc.reuben.listener;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.util.Assert;

import com.skcc.reuben.bean.ReubenExecutor;
import com.skcc.reuben.bus.ReubenServiceMatcher;
import com.skcc.reuben.event.RemoteRequestEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReubenListener implements ApplicationListener<RemoteRequestEvent>, ApplicationContextAware {

	public ReubenListener(ReubenServiceMatcher serviceMatcher) {
		Assert.notNull(serviceMatcher,"ServiceMatcher must be not null!");
		this.serviceMatcher = serviceMatcher;
	}
	
	@Autowired
	private ReubenExecutor rebuenExecutor;
	
	private ReubenServiceMatcher serviceMatcher;

	private ApplicationContext applicationContext;

	@Override
	public void onApplicationEvent(RemoteRequestEvent event) {

		if (!this.serviceMatcher.isFromSelf(event)) {

			String name = event.getName() + "Reuben";

			Object reuben = applicationContext.getBean(name);

			rebuenExecutor.execute(reuben, (event.getPayload()).toArray());
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

}
