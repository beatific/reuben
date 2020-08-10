package com.skcc.reuben.event;

import java.util.List;

public class RemoteResponseEvent extends RemoteCommunicationEvent {

	public RemoteResponseEvent(String name, List<?> payload, String originService, String destinationService) {
		super(name, payload, originService, destinationService);
	}

}
