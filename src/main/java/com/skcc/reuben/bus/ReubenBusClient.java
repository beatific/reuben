package com.skcc.reuben.bus;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ReubenBusClient {

	String INPUT = "reubenCloudBusInput";

	/**
	 * Name of the output channel for Spring Cloud Bus.
	 */
	String OUTPUT = "reubenCloudBusOutput";

	@Output(ReubenBusClient.OUTPUT)
	MessageChannel reubenBusOutput();

	@Input(ReubenBusClient.INPUT)
	SubscribableChannel reubenBusInput();
}
