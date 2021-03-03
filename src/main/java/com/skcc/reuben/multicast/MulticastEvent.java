package com.skcc.reuben.multicast;

import com.skcc.reuben.event.ConvertableRemoteCommunicationEvent;
import com.skcc.reuben.event.RemoteCommunicationEvent;

@SuppressWarnings("serial")
public class MulticastEvent extends ConvertableRemoteCommunicationEvent {

	@SuppressWarnings("unused")
	private MulticastEvent() {}
	
	public MulticastEvent(RemoteCommunicationEvent remoteCommunicationEvent) {
		super(remoteCommunicationEvent);
	}
}