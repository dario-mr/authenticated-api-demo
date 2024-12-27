package com.authenticated.api.demo.config;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtDecoderConfig {

  @Value("${jwt.public-key-base64}")
  private String publicKeyBase64;

  @Bean
  public JwtDecoder jwtDecoder() throws NoSuchAlgorithmException, InvalidKeySpecException {
    var publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
    var publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
    var publicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);

    return NimbusJwtDecoder
        .withPublicKey((RSAPublicKey) publicKey)
        .build();
  }
}
