package com.skcc.reuben.multicast;

import java.lang.annotation.Annotation;

import com.skcc.reuben.annotation.AnnotationMapping;
import com.skcc.reuben.build.MethodHandler;

public class MulticastMapping implements AnnotationMapping {

	@Override
	public Class<? extends Annotation> annotation() {
		return Multicast.class;
	}

	@Override
	public Class<? extends MethodHandler> methodHandler() {
		return MulticastHandler.class;
	}
}
