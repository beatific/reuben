package com.skcc.reuben.unicast;

import com.skcc.reuben.annotation.AbstractMethodHandler;
import com.skcc.reuben.event.ConvertableRemoteCommunicationEvent;

public class UnicastHandler extends AbstractMethodHandler {

	@Override
	protected Class<? extends ConvertableRemoteCommunicationEvent> event() {
		return UnicastEvent.class;
	}
}
