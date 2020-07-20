package com.skcc.reuben.listener;

import org.springframework.context.ApplicationListener;

import com.skcc.reuben.event.RemoteRequestEvent;

public class Listener implements ApplicationListener<RemoteRequestEvent> {

	@Override
	public void onApplicationEvent(RemoteRequestEvent event) {
		
		String name = event.getName();
		
		
	}

}
