package com.skcc.reuben;

public interface ReubenBusConfigurer {

	default boolean primary() {
		return true;
	}

	default boolean inheritParentConfiguration() {
		return true;
	}
}
