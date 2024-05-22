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
		return GroupedOpenApi.builder().group("course").pathsToMatch("/api/course/**").build();
	}

	@Bean
	public GroupedOpenApi matchApi() {
		return GroupedOpenApi.builder().group("match").pathsToMatch("/api/match/**").build();
	}
	
	@Bean
	public GroupedOpenApi memberApi() {
		return GroupedOpenApi.builder().group("member").pathsToMatch("/api/member/**").build();
	}
	
	@Bean
	public GroupedOpenApi reviewApi() {
		return GroupedOpenApi.builder().group("review").pathsToMatch("/api/review/**").build();
	}
	
	@Bean
	public GroupedOpenApi tripApi() {
		return GroupedOpenApi.builder().group("trip").pathsToMatch("/api/trip/**").build();
	}
	
	@Bean
	public GroupedOpenApi mailApi() {
		return GroupedOpenApi.builder().group("email").pathsToMatch("/api/email/**").build();
	}
	
	@Bean
	public GroupedOpenApi followApi() {
		return GroupedOpenApi.builder().group("follow").pathsToMatch("/api/follow/**").build();
	}
	
	@Bean
	public GroupedOpenApi fileApi() {
		return GroupedOpenApi.builder().group("file").pathsToMatch("/api/files/**").build();
	}
	
	@Bean
	public GroupedOpenApi gptApi() {
		return GroupedOpenApi.builder().group("gpt").pathsToMatch("/api/gpt/**").build();
	}
}
