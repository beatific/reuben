package com.skcc.reuben.event;

import com.skcc.reuben.payload.Payload;

public class RemoteResponseEvent extends RemoteCommunicationEvent {

	public RemoteResponseEvent(String name, Payload payload, String originService, String destinationService) {
		super(name, payload, originService, destinationService);
	}

}
