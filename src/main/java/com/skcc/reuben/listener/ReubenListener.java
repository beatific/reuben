package com.skcc.reuben.listener;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

import com.skcc.reuben.ReubenContext;
import com.skcc.reuben.bean.ReubenExecutor;
import com.skcc.reuben.event.RemoteRequestEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReubenListener implements ApplicationListener<RemoteRequestEvent>, ApplicationContextAware {

	@Autowired
	private ReubenExecutor rebuenExecutor;
	
	private ApplicationContext applicationContext;
	
	@Override
	public void onApplicationEvent(RemoteRequestEvent event) {
		
		String name = event.getName() + "Reuben";
		
		Object reuben = applicationContext.getBean(name);
		
		log.error("name[{}], reuben [{}]", name, reuben);
		
		rebuenExecutor.execute(reuben, ((List<?>)event.getSource()).toArray());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}

}
