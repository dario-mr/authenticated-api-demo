package com.authenticated.api.demo.config;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter that validates the access token (JWT) in the header of incoming requests.
 * <p>
 * Validation is done via the public key set as application property: {@code jwt.public-key-base64}.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final PublicKey publicKey;

  public JwtAuthenticationFilter(@Value("${jwt.public-key-base64}") String publicKeyBase64) throws Exception {
    // Decode and generate public key
    var publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
    var publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
    this.publicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    var authToken = request.getHeader("Authorization");
    if (authToken != null && authToken.startsWith("Bearer ")) {
      validateAuthToken(request, authToken);
    }

    filterChain.doFilter(request, response);
  }

  private void validateAuthToken(HttpServletRequest request, String authToken) {
    var jwt = authToken.substring(7);
    try {
      var claims = Jwts.parserBuilder()
          .setSigningKey(publicKey)
          .build()
          .parseClaimsJws(jwt)
          .getBody();

      var email = claims.getSubject();
      var authentication = new UsernamePasswordAuthenticationToken(email, null, List.of());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception e) {
      SecurityContextHolder.clearContext();
    }
  }
}
