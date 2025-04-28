package edu.ntnu.flightbookingbackend.security;

import edu.ntnu.flightbookingbackend.model.User;
import edu.ntnu.flightbookingbackend.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {

    String jwtToken = getJwtToken(request);
    Integer userId = null;

    if (jwtToken != null) {
      try {
        userId = jwtUtil.extractUserId(jwtToken);
      } catch (Exception e) {
        logger.warn("Invalid JWT: " + e.getMessage());
      }
    }

    if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      User user = userRepository.findById(userId).orElse(null);
      if (user != null) {
        AccessUserDetails userDetails = new AccessUserDetails(user);

        if (jwtUtil.validateToken(jwtToken, userDetails)) {
          UsernamePasswordAuthenticationToken authentication =
                  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }

    filterChain.doFilter(request, response);
  }

  private String getJwtToken(HttpServletRequest request) {
    final String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }
}
