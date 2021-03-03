package com.skcc.reuben.event;

import java.util.List;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import lombok.Getter;
import lombok.ToString;

@SuppressWarnings("serial")
@ToString(callSuper = true)
public class RemoteCommunicationEvent extends RemoteApplicationEvent{

	@Getter
	private String name;
	
	@Getter
	private List<?> payload;
	

	protected RemoteCommunicationEvent() {
		name = null;
	}
	
	public RemoteCommunicationEvent(String name, List<?> payload, String originService, String destinationService) {
		super(payload, originService, destinationService);
		this.name = name;
		this.payload = payload;
	}
	
}
