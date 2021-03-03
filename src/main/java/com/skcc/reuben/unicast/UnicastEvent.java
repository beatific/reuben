package com.skcc.reuben.unicast;

import com.skcc.reuben.event.ConvertableRemoteCommunicationEvent;
import com.skcc.reuben.event.RemoteCommunicationEvent;

@SuppressWarnings("serial")
public class UnicastEvent extends ConvertableRemoteCommunicationEvent {

	@SuppressWarnings("unused")
	private UnicastEvent() {}
	
	public UnicastEvent(RemoteCommunicationEvent remoteCommunicationEvent) {
		super(remoteCommunicationEvent);
	}
}