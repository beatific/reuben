package com.skcc.reuben.broadcast;

import com.skcc.reuben.event.ConvertableRemoteCommunicationEvent;
import com.skcc.reuben.event.RemoteCommunicationEvent;

@SuppressWarnings("serial")
public class BroadcastEvent extends ConvertableRemoteCommunicationEvent {

	public BroadcastEvent(RemoteCommunicationEvent remoteCommunicationEvent) {
		super(remoteCommunicationEvent);
	}

}
