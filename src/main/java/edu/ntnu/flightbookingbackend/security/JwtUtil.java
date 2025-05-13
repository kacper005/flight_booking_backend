package edu.ntnu.flightbookingbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Utility class for handling JWT (JSON Web Token) operations.
 *
 * <p>This class provides methods to generate, validate, and extract information from JWTs.
 */
@Component
public class JwtUtil {

  private static final String ROLE_KEY = "roles";
  private final SecretKey secretKey;

  /**
   * Constructor for JwtUtil.
   *
   * @param secret The secret key used for signing the JWTs. This should be a secure and private
   *     key.
   */
  public JwtUtil(@Value("${jwt.secret}") String secret) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
  }

  /**
   * Generates a JWT token for the given user details.
   *
   * @param userDetails The user details for which the token is generated.
   * @return The generated JWT token as a string.
   */
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

  /**
   * Extracts the user ID from the JWT token.
   *
   * @param token The JWT token from which to extract the user ID.
   * @return The user ID as an Integer.
   */
  public Integer extractUserId(String token) {
    String userIdStr = extractClaim(token, Claims::getSubject);
    return Integer.parseInt(userIdStr);
  }

  /**
   * Extracts the email from the JWT token.
   *
   * @param token The JWT token from which to extract the email.
   * @return The email as a String.
   */
  public String extractEmail(String token) {
    return extractAllClaims(token).get("email", String.class);
  }

  /**
   * Validates the JWT token.
   *
   * @param token The JWT token to validate.
   * @param userDetails The user details to compare against.
   * @return True if the token is valid, false otherwise.
   */
  public boolean validateToken(String token, UserDetails userDetails) {
    return userDetails != null && !isTokenExpired(token);
  }

  /**
   * Checks if the JWT token is expired.
   *
   * @param token The JWT token to check.
   * @return True if the token is expired, false otherwise.
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Extracts the expiration date from the JWT token.
   *
   * @param token The JWT token from which to extract the expiration date.
   * @return The expiration date as a Date object.
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts a specific claim from the JWT token.
   *
   * @param token The JWT token from which to extract the claim.
   * @param claimsResolver A function that defines how to extract the claim.
   * @param <T> The type of the claim to extract.
   * @return The extracted claim of type T.
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(extractAllClaims(token));
  }

  /**
   * Extracts all claims from the JWT token.
   *
   * @param token The JWT token from which to extract the claims.
   * @return The claims as a Claims object.
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
  }
}
