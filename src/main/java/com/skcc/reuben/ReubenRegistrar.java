package com.skcc.reuben;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.skcc.reuben.bean.Reuben;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReubenRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

	private ResourceLoader resourceLoader;

	private Environment environment;
	
	private Set<String> basePackages;
	
	ReubenRegistrar() {
	}
	
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		registerDefaultConfiguration(metadata, registry);
		registerReuben(metadata, registry);
	}
	
	private void registerDefaultConfiguration(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		Map<String, Object> defaultAttrs = metadata.getAnnotationAttributes(EnableReuben.class.getName(), true);

		if (defaultAttrs != null && defaultAttrs.containsKey("defaultConfiguration")) {
			String name;
			if (metadata.hasEnclosingClass()) {
				name = "default." + metadata.getEnclosingClassName();
			} else {
				name = "default." + metadata.getClassName();
			}
			registerConfiguration(registry, name, defaultAttrs.get("defaultConfiguration"));
		}
	}
	
	public void registerReuben(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		ClassPathScanningCandidateComponentProvider scanner = getScanner();
		scanner.setResourceLoader(this.resourceLoader);

		AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(Reuben.class);
		scanner.addIncludeFilter(annotationTypeFilter);
		basePackages = getBasePackages(metadata);

		for (String basePackage : basePackages) {
			log.error("basePackage [{}]", basePackage);
			
			Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
			log.error("candidateComponents [{}]", candidateComponents);
			for (BeanDefinition candidateComponent : candidateComponents) {
				
				log.error("candidateComponent [{}]", candidateComponent.getBeanClassName());
				if (candidateComponent instanceof AnnotatedBeanDefinition) {
					
					AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
					AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
					Assert.isTrue(annotationMetadata.isConcrete(),
							"@Reuben can be specified on an concrete class");

					Map<String, Object> attributes = annotationMetadata
							.getAnnotationAttributes(Reuben.class.getCanonicalName());

					String name = getName(attributes);
					registerConfiguration(registry, name, attributes.get("configuration"));
					registerReuben(registry, annotationMetadata, attributes);
					
				}
			}
		}
	}
	
	private void registerReuben(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata,
			Map<String, Object> attributes) {
		
		
		String className = annotationMetadata.getClassName();
		BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(ReubenFactoryBean.class);

		String contextId = getName(attributes);
		definition.addPropertyValue("type", className);
		definition.addPropertyValue("contextId", contextId);
		definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

		String alias = contextId + "Reuben";
		AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
		beanDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, className);

		BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className, new String[] { alias });
		BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
	}
	
	private GenericBeanDefinition createBean(@Nullable String beanClassName) {
	    final GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
	    genericBeanDefinition.setBeanClassName(beanClassName);
	    log.error("beanClassName[{}]", beanClassName);
	    return genericBeanDefinition;
	  }
	
    private void registerReubenBean(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata,
			Map<String, Object> attributes) {
    	
    	final GenericBeanDefinition beanDefinition = createBean(annotationMetadata.getClassName());
    	String contextId = getName(attributes);
    	String alias = contextId + "Reuben";
    	log.error("alias[{}]", alias);
    	registry.registerBeanDefinition(alias, beanDefinition);
    	 
    }

	
	
	private String getName(Map<String, Object> attributes) {
		
		String value = (String) Optional.ofNullable(attributes).orElse(new HashMap<>()).get("name");
		if (StringUtils.hasText(value)) {
			return value;
		}

		throw new IllegalStateException(
				"Propery 'name' must be provided in @" + Reuben.class.getSimpleName());
	}
	
	private void registerConfiguration(BeanDefinitionRegistry registry, Object name, Object configuration) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ReubenSpecification.class);
		builder.addConstructorArgValue(name);
		builder.addConstructorArgValue(configuration);
		registry.registerBeanDefinition(name + "." + ReubenSpecification.class.getSimpleName(),
				builder.getBeanDefinition());
	}


	protected ClassPathScanningCandidateComponentProvider getScanner() {
		return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
			@Override
			protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
				boolean isCandidate = false;
				if (beanDefinition.getMetadata().isIndependent()) {
					if (!beanDefinition.getMetadata().isAnnotation()) {
						isCandidate = true;
					}
				}
				return isCandidate;
			}
		};
	}

	protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
		Map<String, Object> attributes = importingClassMetadata
				.getAnnotationAttributes(EnableReuben.class.getCanonicalName());

		Set<String> basePackages = new HashSet<>();
		for (String pkg : (String[]) attributes.get("value")) {
			if (StringUtils.hasText(pkg)) {
				basePackages.add(pkg);
			}
		}
		for (String pkg : (String[]) attributes.get("basePackages")) {
			if (StringUtils.hasText(pkg)) {
				basePackages.add(pkg);
			}
		}
		for (Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
			basePackages.add(ClassUtils.getPackageName(clazz));
		}

		if (basePackages.isEmpty()) {
			basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
		}
		
		return basePackages;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
}