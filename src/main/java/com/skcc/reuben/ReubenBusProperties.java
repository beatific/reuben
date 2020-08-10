package com.skcc.reuben;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.skcc.reuben.build.DefaultMethodHandler;
import com.skcc.reuben.build.InvocationHandlerFactory;
import com.skcc.reuben.build.MethodHandler;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("reuben.bus")
public class ReubenBusProperties {

	private boolean defaultToProperties = true;

	private String defaultConfig = "default";
	
	@Getter @Setter
	private String id = "application";

	private Map<String, ReubenBusConfig> config = new HashMap<>();

	public boolean isDefaultToProperties() {
		return this.defaultToProperties;
	}

	public void setDefaultToProperties(boolean defaultToProperties) {
		this.defaultToProperties = defaultToProperties;
	}

	public String getDefaultConfig() {
		return this.defaultConfig;
	}

	public void setDefaultConfig(String defaultConfig) {
		this.defaultConfig = defaultConfig;
	}

	public Map<String, ReubenBusConfig> getConfig() {
		return this.config;
	}

	public void setConfig(Map<String, ReubenBusConfig> config) {
		this.config = config;
	}

	@Data
	public static class ReubenBusConfig {
		
		Class<DefaultMethodHandler> defaultMethodHandler;
		Class<InvocationHandlerFactory> invocationHandlerFactory;
		Map<Class<Annotation>, Class<MethodHandler>> mapping;

	}

}