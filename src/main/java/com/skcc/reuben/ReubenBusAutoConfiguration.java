package com.skcc.reuben;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.autoconfigure.LifecycleMvcEndpointAutoConfiguration;
import org.springframework.cloud.bus.BusPathMatcher;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.ConditionalOnBusEnabled;
import org.springframework.cloud.bus.ServiceMatcherAutoConfiguration;
import org.springframework.cloud.bus.SpringCloudBusClient;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.PathMatcher;

import com.skcc.reuben.bean.DefaultReubenExecutor;
import com.skcc.reuben.bean.ReubenExecutor;
import com.skcc.reuben.bus.ReubenServiceMatcher;
import com.skcc.reuben.event.RemoteCommunicationEvent;
import com.skcc.reuben.listener.ReubenListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RemoteApplicationEventScan
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({ ReubenBusProperties.class,})
@ConditionalOnBusEnabled
@EnableBinding(SpringCloudBusClient.class)
@AutoConfigureBefore(BindingServiceConfiguration.class)
// so stream bindings work properly
@AutoConfigureAfter({ LifecycleMvcEndpointAutoConfiguration.class,
		ServiceMatcherAutoConfiguration.class })
public class ReubenBusAutoConfiguration implements ApplicationEventPublisherAware {
	
	private ReubenServiceMatcher serviceMatcher;

	private final BindingServiceProperties bindings;

	private final BusProperties bus;

	private MessageChannel cloudBusOutboundChannel;

	private ApplicationEventPublisher applicationEventPublisher;
	
	public static final String CLOUD_CONFIG_NAME_PROPERTY = "spring.cloud.config.name";
	
	public ReubenBusAutoConfiguration(BindingServiceProperties bindings, BusProperties bus) {
		this.bindings = bindings;
		this.bus = bus;
	}
	
	
	@Autowired
	public ReubenServiceMatcher serviceMatcher(@BusPathMatcher PathMatcher pathMatcher,
			ReubenBusProperties properties, Environment environment) {
		String[] configNames = environment.getProperty(CLOUD_CONFIG_NAME_PROPERTY,
				String[].class, new String[] {});
		ReubenServiceMatcher serviceMatcher = new ReubenServiceMatcher(pathMatcher,
				properties.getId(), configNames);
		this.serviceMatcher = serviceMatcher;
		return serviceMatcher;
	}
	
	
	@PostConstruct
	public void init() {
		BindingProperties inputBinding = this.bindings.getBindings()
				.get(SpringCloudBusClient.INPUT);
		if (inputBinding == null) {
			this.bindings.getBindings().put(SpringCloudBusClient.INPUT,
					new BindingProperties());
		}
		BindingProperties input = this.bindings.getBindings()
				.get(SpringCloudBusClient.INPUT);
		if (input.getDestination() == null
				|| input.getDestination().equals(SpringCloudBusClient.INPUT)) {
			input.setDestination(this.bus.getDestination());
		}
		BindingProperties outputBinding = this.bindings.getBindings()
				.get(SpringCloudBusClient.OUTPUT);
		if (outputBinding == null) {
			this.bindings.getBindings().put(SpringCloudBusClient.OUTPUT,
					new BindingProperties());
		}
		BindingProperties output = this.bindings.getBindings()
				.get(SpringCloudBusClient.OUTPUT);
		if (output.getDestination() == null
				|| output.getDestination().equals(SpringCloudBusClient.OUTPUT)) {
			output.setDestination(this.bus.getDestination());
		}
	}
	
	@Override
	public void setApplicationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	@Autowired
	@Output(SpringCloudBusClient.OUTPUT)
	public void setCloudBusOutboundChannel(MessageChannel cloudBusOutboundChannel) {
		this.cloudBusOutboundChannel = cloudBusOutboundChannel;
	}
	
	
	@EventListener(classes = RemoteCommunicationEvent.class)
	public void acceptLocal(RemoteCommunicationEvent event) {
		
		log.error("event.getOriginService[{}]", event.getOriginService());
		log.error("serviceMatcher.getServiceId[{}]", serviceMatcher.getServiceId());
		if (this.serviceMatcher.isFromSelf(event)) {
//				&& !(event instanceof AckRemoteApplicationEvent)) {
//			if (log.isDebugEnabled()) {
				log.error("Sending remote event on bus: " + event);
//			}
			this.cloudBusOutboundChannel.send(MessageBuilder.withPayload(event).build());
		}
	}

	@StreamListener(SpringCloudBusClient.INPUT)
	public void acceptRemote(RemoteCommunicationEvent event) {
		log.error("Received remote event from bus: " + event);
//		if (event instanceof AckRemoteApplicationEvent) {
//			if (this.bus.getTrace().isEnabled() && !this.serviceMatcher.isFromSelf(event)
//					&& this.applicationEventPublisher != null) {
//				this.applicationEventPublisher.publishEvent(event);
//			}
//			// If it's an ACK we are finished processing at this point
//			return;
//		}
		

//		if (log.isDebugEnabled()) {
//			log.debug("Received remote event from bus: " + event);
//		}

		if (this.serviceMatcher.isForSelf(event)
				&& this.applicationEventPublisher != null) {
			if (!this.serviceMatcher.isFromSelf(event)) {
				this.applicationEventPublisher.publishEvent(event);
			}
//			if (this.bus.getAck().isEnabled()) {
//				AckRemoteApplicationEvent ack = new AckRemoteApplicationEvent(this,
//						this.serviceMatcher.getServiceId(),
//						this.bus.getAck().getDestinationService(),
//						event.getDestinationService(), event.getId(), event.getClass());
//				this.cloudBusOutboundChannel
//						.send(MessageBuilder.withPayload(ack).build());
//				this.applicationEventPublisher.publishEvent(ack);
//			}
		}
//		if (this.bus.getTrace().isEnabled() && this.applicationEventPublisher != null) {
//			// We are set to register sent events so publish it for local consumption,
//			// irrespective of the origin
//			this.applicationEventPublisher.publishEvent(new SentApplicationEvent(this,
//					event.getOriginService(), event.getDestinationService(),
//					event.getId(), event.getClass()));
//		}
	}
	
	@ConditionalOnMissingBean
	@Bean
	ReubenExecutor reubenExecutor() {
		return new DefaultReubenExecutor();
	}
	
	@Bean
	public ReubenListener reubenListener() {
		return new ReubenListener();
	}
	
	@Autowired(required = false)
	private List<ReubenBusSpecification> configurations = new ArrayList<>();
	
	@Bean
	public ReubenBusContext reubenContext() {
		ReubenBusContext context = new ReubenBusContext();
		context.setConfigurations(this.configurations);
		return context;
	}
}
