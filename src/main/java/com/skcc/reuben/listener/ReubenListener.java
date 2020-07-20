package com.skcc.reuben.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import com.skcc.reuben.ReubenContext;
import com.skcc.reuben.bean.ReubenExecutor;
import com.skcc.reuben.event.RemoteRequestEvent;

public class ReubenListener implements ApplicationListener<RemoteRequestEvent> {

	@Autowired 
	private ReubenContext reubenContext;
	
	@Autowired
	private ReubenExecutor rebuenExecutor;
	
	@Override
	public void onApplicationEvent(RemoteRequestEvent event) {
		
		String name = event.getName();
		Object reuben = reubenContext.getReuben(name);
		
		rebuenExecutor.execute(reuben, event.getSource());
	}

}
