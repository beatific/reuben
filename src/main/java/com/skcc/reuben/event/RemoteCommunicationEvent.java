package com.skcc.reuben.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import com.skcc.reuben.payload.Payload;

import lombok.Getter;

@SuppressWarnings("serial")
public class RemoteCommunicationEvent extends RemoteApplicationEvent {

	@Getter
	private String name;
	
	public RemoteCommunicationEvent(String name, Payload payload, String originService, String destinationService) {
		super(payload, originService, destinationService);
		this.name = name;
	}
	
}
