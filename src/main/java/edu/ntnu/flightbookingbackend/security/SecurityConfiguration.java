package edu.ntnu.flightbookingbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Creates AuthenticationManager - set up authentication type. The @EnableMethodSecurity is needed
 * so that each endpoint can specify wich role it requires.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

  @Autowired private AccessUserService userDetailsService;

  @Autowired private JwtRequestFilter jwtRequestFilter;

  /**
   * This method will be called automatically by the framework to find the authentication to use.
   *
   * @param http HttpSecurity setting builder
   * @throws Exception When security configuration fails
   */
  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authz ->
                authz
                    // Open endpoints
                    .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**")
                    .permitAll()
                    .requestMatchers("/flights/**", "/airports/**")
                    .permitAll()

                    // Feedback: Only logged in users kan create and update feedback
                    .requestMatchers(HttpMethod.POST, "/feedback/**")
                    .hasAuthority("USER")
                    .requestMatchers(HttpMethod.PUT, "/feedback/**")
                    .hasAuthority("USER")
                    .requestMatchers(HttpMethod.DELETE, "/feedback/**")
                    .hasAuthority("USER")

                    // Booking: Only logged in users can book flights
                    .requestMatchers("/booking/**")
                    .hasAuthority("USER")

                    // Flight-administration: Only ADMIN can add, delete and update flights and
                    // airports
                    .requestMatchers(HttpMethod.POST, "/flights/**")
                    .hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/flights/**")
                    .hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/flights/**")
                    .hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/airports/**")
                    .hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/airports/**")
                    .hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/airports/**")
                    .hasAuthority("ADMIN")

                    // Other endpoints require authentication
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
