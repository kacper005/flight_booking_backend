package edu.ntnu.flightbookingbackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final String ROLE_KEY = "roles";
  private final SecretKey secretKey;

  public JwtUtil(@Value("${jwt_secret_key}") String secret) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String generateToken(UserDetails userDetails) {
    long timeNow = System.currentTimeMillis();
    long expirationTime = timeNow + 60 * 60 * 1000; // 1 hour

    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .claim(ROLE_KEY, userDetails.getAuthorities())
        .setIssuedAt(new Date(timeNow))
        .setExpiration(new Date(expirationTime))
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractUsername(String token) throws JwtException {
    return extractClaim(token, Claims::getSubject);
  }

  public boolean validateToken(String token, UserDetails userDetails) throws JwtException {
    final String username = extractUsername(token);
    return userDetails != null &&
            username.equals(userDetails.getUsername()) &&
            !isTokenExpired(token);
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
}
