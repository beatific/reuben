package com.skcc.reuben;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.Assert;

import com.skcc.reuben.annotation.AnnotationMapping;
import com.skcc.reuben.build.AbstractAnnotationMapping;
import com.skcc.reuben.build.AnnotationMethodMapping;
import com.skcc.reuben.build.DefaultMethodHandler;
import com.skcc.reuben.build.InvocationHandlerFactory;
import com.skcc.reuben.build.MethodHandler;
import com.skcc.reuben.build.ReubenBuilder;
import com.skcc.reuben.build.ReubenBuilderCustomizer;
import com.skcc.reuben.context.ReubenContext;

import lombok.Getter;
import lombok.Setter;

public class ReubenBusFactoryBean implements FactoryBean<Object>, InitializingBean, ApplicationContextAware {

	@Getter @Setter
	private Class<?> type;

	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private String contextId;

	@Getter @Setter
	private boolean inheritParentContext = true;

	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() {
		
		Assert.hasText(this.name, "Name must be set");
		Assert.hasText(this.contextId, "contextId must be set");
	}

	protected ReubenBuilder reuben(ReubenContext context) {

		ReubenBuilder builder = get(context, ReubenBuilder.class);
		
		configureReuben(context, builder);
		applyBuildCustomizers(context, builder);

		return builder;
	}

	private void applyBuildCustomizers(ReubenContext context, ReubenBuilder builder) {
		Map<String, ReubenBuilderCustomizer> customizerMap = context
				.getInstances(contextId, ReubenBuilderCustomizer.class);

		if (customizerMap != null) {
			customizerMap.values().stream()
					.sorted(AnnotationAwareOrderComparator.INSTANCE)
					.forEach(builderCustomizer -> builderCustomizer
							.customize(builder));
		}
	}

	protected void configureReuben(ReubenContext context, ReubenBuilder builder) {
		ReubenBusProperties properties = this.applicationContext.getBean(ReubenBusProperties.class);

		ReubenBusConfigurer reubenBusConfigurer = getOptional(context,ReubenBusConfigurer.class);
		setInheritParentContext(reubenBusConfigurer.inheritParentConfiguration());

		if (properties != null && inheritParentContext) {
			if (properties.isDefaultToProperties()) {
				configureUsingConfiguration(context, builder);
				configureUsingProperties(
						properties.getConfig().get(properties.getDefaultConfig()),
						builder);
				configureUsingProperties(properties.getConfig().get(this.contextId),
						builder);
			}
			else {
				configureUsingProperties(
						properties.getConfig().get(properties.getDefaultConfig()),
						builder);
				configureUsingProperties(properties.getConfig().get(this.contextId),
						builder);
				configureUsingConfiguration(context, builder);
			}
		}
		else {
			configureUsingConfiguration(context, builder);
		}
	}
	
	protected void configureUsingConfiguration(ReubenContext context,
			ReubenBuilder builder) {
		
		DefaultMethodHandler defaultMethodHandler = get(context, DefaultMethodHandler.class);
		InvocationHandlerFactory invocationHandlerFactory = get(context, InvocationHandlerFactory.class);
		AnnotationMethodMapping annotationMethodMapping = get(context, AnnotationMethodMapping.class);
		
		Collection<AnnotationMapping> mappings = getInheritedAwareInstances(context, AnnotationMapping.class).values();
		mappings.forEach(mapping -> {
			MethodHandler methodHandler = getOrInstantiate(mapping.methodHandler());
			methodHandler.setAnnotationMapping(mapping);
			annotationMethodMapping.register(mapping.annotation(), methodHandler);
		});
		
		builder.setDefaultMethodHandler(defaultMethodHandler);
		builder.setInvocationHandlerFactory(invocationHandlerFactory);
		builder.setMapping(annotationMethodMapping);
	}

	protected void configureUsingProperties(
			ReubenBusProperties.ReubenBusConfig config,
			ReubenBuilder builder) {
		
		if (config == null) {
			return;
		}
		
		if (config.getDefaultMethodHandler() != null) {
			DefaultMethodHandler defaultMethodHandler = getOrInstantiate(config.getDefaultMethodHandler());
			builder.setDefaultMethodHandler(defaultMethodHandler);
		}
		
		if (config.getInvocationHandlerFactory() != null) {
			DefaultMethodHandler defaultMethodHandler = getOrInstantiate(config.getDefaultMethodHandler());
			builder.setDefaultMethodHandler(defaultMethodHandler);
		}
		
		if (config.getMapping() != null) {
			AnnotationMethodMapping annotationMethodMapping = get(AnnotationMethodMapping.class);
			
			config.getMapping().forEach((annotationClass, methodHandlerClass) -> {
				MethodHandler methodHandler = getOrInstantiate(methodHandlerClass);
				methodHandler.setAnnotationMapping(new AbstractAnnotationMapping() {

					@Override
					public Class<? extends Annotation> annotation() {
						return annotationClass;
					}

					@Override
					public Class<? extends MethodHandler> methodHandler() {
						return methodHandlerClass;
					}
					
				});
				annotationMethodMapping.register(annotationClass, methodHandler);
			});
			
			builder.setMapping(annotationMethodMapping);
		}

	}

	private <T> T getOrInstantiate(Class<T> tClass) {
		try {
			return this.applicationContext.getBean(tClass);
		}
		catch (NoSuchBeanDefinitionException e) {
			return BeanUtils.instantiateClass(tClass);
		}
	}
	
	protected <T> T get(Class<T> type) {
		T instance = applicationContext.getBean(type);
		if (instance == null) {
			throw new IllegalStateException(
					"No bean found of type " + type + " for " + this.contextId);
		}
		return instance;
	}

	protected <T> T get(ReubenContext context, Class<T> type) {
		T instance = context.getInstance(this.contextId, type);
		if (instance == null) {
			throw new IllegalStateException(
					"No bean found of type " + type + " for " + this.contextId);
		}
		return instance;
	}

	protected <T> T getOptional(ReubenContext context, Class<T> type) {
		return context.getInstance(this.contextId, type);
	}

	protected <T> T getInheritedAwareOptional(ReubenContext context, Class<T> type) {
		if (inheritParentContext) {
			return getOptional(context, type);
		}
		else {
			return context.getInstanceWithoutAncestors(this.contextId, type);
		}
	}

	protected <T> Map<String, T> getInheritedAwareInstances(ReubenContext context,
			Class<T> type) {
		if (inheritParentContext) {
			return context.getInstances(this.contextId, type);
		}
		else {
			return context.getInstancesWithoutAncestors(this.contextId, type);
		}
	}

	@Override
	public Object getObject() {
		ReubenContext context = this.applicationContext.getBean(ReubenContext.class);
		ReubenBuilder builder = reuben(context);
		
		return builder.build(this.type);
	}

	@Override
	public Class<?> getObjectType() {
		return this.type;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}


	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.applicationContext = context;
	}

}
