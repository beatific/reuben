package com.skcc.reuben.broadcast;

import com.skcc.reuben.event.ConvertableRemoteCommunicationEvent;
import com.skcc.reuben.event.RemoteCommunicationEvent;

import lombok.ToString;

@SuppressWarnings("serial")
public class BroadcastEvent extends ConvertableRemoteCommunicationEvent {

	@SuppressWarnings("unused")
	private BroadcastEvent() {}
	
	public BroadcastEvent(RemoteCommunicationEvent remoteCommunicationEvent) {
		super(remoteCommunicationEvent);
	}

}
