package com.skcc.reuben.event;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@SuppressWarnings("serial")
public abstract class ConvertableRemoteCommunicationEvent extends RemoteRequestEvent {

	
	protected ConvertableRemoteCommunicationEvent() {}
	
	public ConvertableRemoteCommunicationEvent(RemoteCommunicationEvent remoteCommunicationEvent) {
		
		super(remoteCommunicationEvent.getName(), (List<?>)remoteCommunicationEvent.getSource(), remoteCommunicationEvent.getOriginService(), remoteCommunicationEvent.getDestinationService());
	}
	
	public static <T extends ConvertableRemoteCommunicationEvent> T convert(Class<T> type, RemoteCommunicationEvent remoteCommunicationEvent) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<T> constructor = type.getConstructor(RemoteCommunicationEvent.class);
		return constructor.newInstance(remoteCommunicationEvent);
	}
}
