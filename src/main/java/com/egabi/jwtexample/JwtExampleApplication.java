package com.egabi.jwtexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class JwtExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtExampleApplication.class, args);
	}

        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOrigins("*")
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "PATCH")
                            .allowedHeaders("Access-Control-Allow-Headers", "Access-Control-Allow-Origin",
                                "Access-Control-Request-Method", "Authorization", "Access-Control-Request-Headers", "Origin",
                                "Cache-Control", "Content-Type")
                            .allowCredentials(false);
                }
            };
        }

}
