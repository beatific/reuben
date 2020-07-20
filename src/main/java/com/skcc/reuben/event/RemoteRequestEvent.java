package com.skcc.reuben.event;

import com.skcc.reuben.payload.Payload;

public class RemoteRequestEvent extends RemoteCommunicationEvent {

	public RemoteRequestEvent(String name, Payload payload, String originService, String destinationService) {
		super(name, payload, originService, destinationService);
		// TODO Auto-generated constructor stub
	}

}
