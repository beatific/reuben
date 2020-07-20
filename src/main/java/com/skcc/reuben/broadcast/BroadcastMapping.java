package com.skcc.reuben.broadcast;

import java.lang.annotation.Annotation;

import com.skcc.reuben.annotation.AnnotationMapping;
import com.skcc.reuben.build.MethodHandler;
import com.skcc.reuben.event.ConvertableRemoteCommunicationEvent;

public class BroadcastMapping implements AnnotationMapping {

	@Override
	public Class<? extends Annotation> annotation() {
		return Broadcast.class;
	}

	@Override
	public Class<? extends MethodHandler> methodHandler() {
		return BroadcastHandler.class;
	}

}
