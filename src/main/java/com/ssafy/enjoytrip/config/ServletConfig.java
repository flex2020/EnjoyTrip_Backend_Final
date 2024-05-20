package com.ssafy.enjoytrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
@Configuration
@EnableWebMvc
@ComponentScan({"com.ssafy.enjoytrip.*.controller"})
public class ServletConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String projectRoot = System.getProperty("user.dir");
	    String filesPath = "file:" + projectRoot + "\\src\\main\\webapp\\WEB-INF\\img\\";
	    registry.addResourceHandler("/api/files/**")
	            .addResourceLocations(filesPath);
	}
}
