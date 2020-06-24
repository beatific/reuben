package com.skcc.reuben.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import lombok.Getter;
import lombok.Setter;

public class RemoteCommunicationEvent extends RemoteApplicationEvent {

	@Getter @Setter
	private String name;
}
