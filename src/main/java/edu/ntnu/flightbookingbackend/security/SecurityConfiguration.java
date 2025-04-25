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
   * @throws Exception if an error occurs
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

                    // Everyone can view flights, airports, airlines, price
                    .requestMatchers(HttpMethod.GET, "/flights/search")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/flights/roundtrip")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/flights/oneway")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/flights/return")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/airports/**", "/airlines/**", "/prices/**", "/flights/**")
                    .permitAll()

                    // ADMIN can manage flights, airports, airlines, prices
                    .requestMatchers(
                        HttpMethod.POST,
                        "/flights/**",
                        "/airports/**",
                        "/airlines/**",
                        "/prices/**")
                    .hasAuthority("ROLE_ADMIN")
                    .requestMatchers(
                        HttpMethod.PUT, "/flights/**", "/airports/**", "/airlines/**", "/prices/**")
                    .hasAuthority("ROLE_ADMIN")
                    .requestMatchers(
                        HttpMethod.DELETE,
                        "/flights/**",
                        "/airports/**",
                        "/airlines/**",
                        "/prices/**")
                    .hasAuthority("ROLE_ADMIN")

                    // ADMIN can manage all /users/** endpoints
                    .requestMatchers("/users/**")
                    .hasAuthority("ROLE_ADMIN")

                    // Feedback:
                    // Everyone can read
                    .requestMatchers(HttpMethod.GET, "/feedback/**")
                    .permitAll()
                    // USER or ADMIN can post, update, delete
                    .requestMatchers(HttpMethod.POST, "/feedback/**")
                    .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/feedback/**")
                    .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/feedback/**")
                    .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                    // Booking:
                    // USER or ADMIN can book, update, delete
                    .requestMatchers("/booking/**")
                    .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                    // (Optional) Protect admin route
                    .requestMatchers("/admin/**")
                    .hasAuthority("ROLE_ADMIN")

                    // Any other endpoint requires login
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
