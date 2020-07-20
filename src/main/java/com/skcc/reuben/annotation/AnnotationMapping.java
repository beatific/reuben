package com.skcc.reuben.annotation;

import java.lang.annotation.Annotation;

import com.skcc.reuben.build.MethodHandler;
import com.skcc.reuben.event.ConvertableRemoteCommunicationEvent;

public interface AnnotationMapping {

    public Class<? extends Annotation> annotation();
    
    public Class<? extends MethodHandler> methodHandler();
}
