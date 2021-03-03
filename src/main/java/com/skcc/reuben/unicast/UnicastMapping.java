package com.skcc.reuben.unicast;

import java.lang.annotation.Annotation;

import com.skcc.reuben.annotation.AnnotationMapping;
import com.skcc.reuben.build.MethodHandler;

public class UnicastMapping implements AnnotationMapping {

	@Override
	public Class<? extends Annotation> annotation() {
		return Unicast.class;
	}

	@Override
	public Class<? extends MethodHandler> methodHandler() {
		return UnicastHandler.class;
	}

}
