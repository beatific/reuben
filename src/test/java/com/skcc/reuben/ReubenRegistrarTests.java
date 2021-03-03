package com.skcc.reuben;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.skcc.reuben.bean.Reuben;
import com.skcc.reuben.broadcast.Broadcast;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReubenRegistrarTests {

	@ReubenBus(name = "reubenBus")
	protected interface ReubenBusTest {
		
		@Broadcast(name = "test")
		void reubenTest(String test);

	}

	@Configuration(proxyBeanMethods = false)
	@EnableAutoConfiguration
	@EnableReubenBus
	protected static class ReubenBusTestConfig {

	}
	
//	@Reuben(name="test")
//	public class ReubenTest2 {
//		
//		@Action
//		void reubenTest(String test) {
//			
//			log.error("test[{}]", test);
//			assertEquals("test", test);
//		}
//
//	}

	@Configuration(proxyBeanMethods = false)
	@EnableAutoConfiguration
	@EnableReuben
	public static class ReubenTestConfig {

		@Bean
		public ScanTest scan() {
			return new ScanTest();
		}
	}
	
//	@Before
	public void testBefore() {
		
		ConfigurableApplicationContext context = SpringApplication.run(new Class[] { ReubenBusTestConfig.class, ReubenBusAutoConfiguration.class},
				new String[] { "--spring.main.allow-bean-definition-overriding=true" });
		ReubenBusTest reubenBus = context.getBean(ReubenBusTest.class);
		reubenBus.reubenTest("test");
		
	}
	
	@Test
	public void testDefault() {
		
		ConfigurableApplicationContext context = SpringApplication.run(new Class[] { ReubenTestConfig.class , ReubenAutoConfiguration.class },
				new String[] { "--spring.main.allow-bean-definition-overriding=true" });
//		ReubenTest reuben = (ReubenTest)context.getBean("testReuben");
		ReubenTest reuben1 = context.getBean(ReubenTest.class);
//		ReubenContext reubenContext = context.getBean(ReubenContext.class);
//		ReubenTest reuben2 = reubenContext.getReuben("test");
		
//		log.error("reuben2[{}]", reuben2);
//		assertEquals(reuben1, reuben2);
	}
	
	protected ClassPathScanningCandidateComponentProvider getScanner(Environment environment) {
		return new ClassPathScanningCandidateComponentProvider(false, environment) {
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
	
	@Data
	public static class ScanTest implements ResourceLoaderAware, EnvironmentAware {

		private ResourceLoader resourceLoader;

		private Environment environment;
		
		
	}
	
//	@Test
	public void testScan() {
		
		ConfigurableApplicationContext context = SpringApplication.run(new Class[] { ReubenTestConfig.class },
				new String[] { "--spring.main.allow-bean-definition-overriding=true" });
		ScanTest scan = context.getBean(ScanTest.class);
		
		ClassPathScanningCandidateComponentProvider scanner = getScanner(scan.getEnvironment());
		scanner.setResourceLoader(scan.getResourceLoader());
		
		AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(Reuben.class);
		scanner.addIncludeFilter(annotationTypeFilter);
		String basePackage = "com.skcc.reuben";

		Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
		log.error("candidateComponents [{}]", candidateComponents);
		
		assertNotNull(candidateComponents);
		assertNotEquals(0, candidateComponents.size());
		
	}
	
}
