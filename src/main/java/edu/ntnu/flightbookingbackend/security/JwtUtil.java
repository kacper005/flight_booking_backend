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

  public JwtUtil(@Value("${jwt.secret}") String secret) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String generateToken(AccessUserDetails userDetails) {
    long timeNow = System.currentTimeMillis();
    long expirationTime = timeNow + 60 * 60 * 1000; // 1 hour

    return Jwts.builder()
            .setSubject(String.valueOf(userDetails.getUserId())) // Nå bruker vi userId
            .claim(ROLE_KEY, userDetails.getAuthorities())
            .claim("email", userDetails.getUsername())
            .setIssuedAt(new Date(timeNow))
            .setExpiration(new Date(expirationTime))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
  }

  public Integer extractUserId(String token) {
    String userIdStr = extractClaim(token, Claims::getSubject);
    return Integer.parseInt(userIdStr);
  }

  public String extractEmail(String token) {
    return extractAllClaims(token).get("email", String.class);
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    return userDetails != null && !isTokenExpired(token);
  }


  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(extractAllClaims(token));
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
  }
}
