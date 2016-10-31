/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.d4dl;

import com.d4dl.data.LeadRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.util.List;

/**
 *
 */
@SpringBootApplication
@Configuration
@EnableSpringConfigured
@ComponentScan("com.d4dl.model, com.d4dl.data, com.d4dl.controller, com.d4dl.config")
@EnableSwagger2
@Import({Swagger2DocumentationConfiguration.class, springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class})
public class LeadashApplication implements RepositoryRestConfigurer {

	@Autowired
	private LeadRepository orderRepository;

	public static void main(String[] args) {
		SpringApplication.run(LeadashApplication.class, args);
	}




	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

	}

	@Override
	public void configureConversionService(ConfigurableConversionService conversionService) {

	}

	@Override
	public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
	}

	@Override
	public void configureExceptionHandlerExceptionResolver(ExceptionHandlerExceptionResolver exceptionResolver) {

	}

	@Override
	public void configureHttpMessageConverters(List<HttpMessageConverter<?>> messageConverters) {

	}

	@Override
	public void configureJacksonObjectMapper(ObjectMapper objectMapper) {

	}

}
