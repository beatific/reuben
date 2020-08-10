package com.skcc.reuben.event;

import java.util.List;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import lombok.Getter;

@SuppressWarnings("serial")
public class RemoteCommunicationEvent extends RemoteApplicationEvent{

	@Getter
	private String name;
	

	protected RemoteCommunicationEvent() {
		name = null;
	}
	
	public RemoteCommunicationEvent(String name, List<?> payload, String originService, String destinationService) {
		super(payload, originService, destinationService);
		this.name = name;
	}
	
}
