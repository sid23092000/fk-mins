package com.customer.experience;  // or any package where you prefer to put this class

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// Allow CORS for all paths
		registry.addMapping("/**")  // Allow all endpoints
				.allowedOrigins("http://localhost:3000")  // Allow React app on localhost:3000 (change as needed)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow these HTTP methods
				.allowedHeaders("*")  // Allow all headers
				.allowCredentials(true);  // Allow credentials (cookies, auth tokens)
	}
}
