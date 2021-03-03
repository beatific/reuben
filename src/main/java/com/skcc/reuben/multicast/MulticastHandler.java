package com.skcc.reuben.multicast;

import com.skcc.reuben.annotation.AbstractMethodHandler;
import com.skcc.reuben.event.ConvertableRemoteCommunicationEvent;

public class MulticastHandler extends AbstractMethodHandler {

	@Override
	protected Class<? extends ConvertableRemoteCommunicationEvent> event() {
		return MulticastEvent.class;
	}
}
