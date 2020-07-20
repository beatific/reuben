package com.skcc.reuben.broadcast;

import com.skcc.reuben.annotation.AbstractMethodHandler;
import com.skcc.reuben.event.ConvertableRemoteCommunicationEvent;

public class BroadcastHandler extends AbstractMethodHandler {

	@Override
	protected Class<? extends ConvertableRemoteCommunicationEvent> event() {
		return BroadcastEvent.class;
	}

}
