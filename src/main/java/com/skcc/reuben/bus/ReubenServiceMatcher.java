package com.skcc.reuben.bus;

import org.springframework.util.PathMatcher;

import com.skcc.reuben.event.RemoteCommunicationEvent;

public class ReubenServiceMatcher {

	private final PathMatcher matcher;

	private final String id;

	private String[] configNames = new String[] {};

	public ReubenServiceMatcher(PathMatcher matcher, String id) {
		this.matcher = matcher;
		this.id = id;
	}

	public ReubenServiceMatcher(PathMatcher matcher, String id, String[] configNames) {
		this(matcher, id);

		int colonIndex = id.indexOf(":");
		if (colonIndex >= 0) {
			// if the id contains profiles and port, append them to the config names
			String profilesAndPort = id.substring(colonIndex);
			for (int i = 0; i < configNames.length; i++) {
				configNames[i] = configNames[i] + profilesAndPort;
			}
		}
		this.configNames = configNames;
	}

	public boolean isFromSelf(RemoteCommunicationEvent event) {
		String originService = event.getOriginService();
		String serviceId = getServiceId();
		return this.matcher.match(originService, serviceId);
	}

	public boolean isForSelf(RemoteCommunicationEvent event) {
		String destinationService = event.getDestinationService();
		if (destinationService == null || destinationService.trim().isEmpty()
				|| this.matcher.match(destinationService, getServiceId())) {
			return true;
		}

		// Check all potential config names instead of service name
		for (String configName : this.configNames) {
			if (this.matcher.match(destinationService, configName)) {
				return true;
			}
		}

		return false;
	}

	public String getServiceId() {
		return this.id;
	}
}
