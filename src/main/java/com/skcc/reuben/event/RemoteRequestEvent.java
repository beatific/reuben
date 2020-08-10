package com.skcc.reuben.event;

import java.util.List;

@SuppressWarnings("serial")
public class RemoteRequestEvent extends RemoteCommunicationEvent {

	public RemoteRequestEvent() {}
	public RemoteRequestEvent(String name, List<?> payload, String originService, String destinationService) {
		super(name, payload, originService, destinationService);
	}

}
