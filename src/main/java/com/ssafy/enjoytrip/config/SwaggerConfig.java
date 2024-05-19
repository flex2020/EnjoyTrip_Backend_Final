package com.ssafy.enjoytrip.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		Info info = new Info().title("EnjoyTrip_Backend API 명세서");

		return new OpenAPI().components(new Components()).info(info);
	}

	@Bean
	public GroupedOpenApi courseApi() {
		return GroupedOpenApi.builder().group("course").pathsToMatch("/course/**").build();
	}

	@Bean
	public GroupedOpenApi matchApi() {
		return GroupedOpenApi.builder().group("match").pathsToMatch("/match/**").build();
	}
	
	@Bean
	public GroupedOpenApi memberApi() {
		return GroupedOpenApi.builder().group("member").pathsToMatch("/member/**").build();
	}
	
	@Bean
	public GroupedOpenApi reviewApi() {
		return GroupedOpenApi.builder().group("review").pathsToMatch("/review/**").build();
	}
	
	@Bean
	public GroupedOpenApi tripApi() {
		return GroupedOpenApi.builder().group("trip").pathsToMatch("/trip/**").build();
	}
}