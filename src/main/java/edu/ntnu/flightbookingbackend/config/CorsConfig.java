package edu.ntnu.flightbookingbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing) in the application. This
 * allows the frontend application to communicate with the backend API.
 */
@Configuration
public class CorsConfig {

  /**
   * Configures CORS settings for the application.
   *
   * @return a WebMvcConfigurer that sets up CORS mappings.
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOrigins(
                "http://localhost:5173", "http://group03.web-tek.ninja", "http://10.212.25.210")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
      }
    };
  }
}
