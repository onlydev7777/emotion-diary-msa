package com.emotion.emotiondiarydiary.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProvider {

  private final String accessTokenHeader;
  private final String refreshTokenHeader;
  private final String iss;
  private final String secretKey;
  private final long expirationTime;
  private final String tokenPrefix;
  private final long refreshExpirationTime;
  private final static String CLAIMS_KEY = "payload";
  private final ObjectMapper objectMapper = new ObjectMapper();

  public JwtProvider(String accessTokenHeader, String refreshTokenHeader, String iss, String secretKey, long expirationTime,
      long refreshExpirationTime, String tokenPrefix) {
    this.accessTokenHeader = accessTokenHeader;
    this.refreshTokenHeader = refreshTokenHeader;
    this.iss = iss;
    this.secretKey = secretKey;
    this.expirationTime = expirationTime * 1000;    // 설정 파일에서 seconds 단위로 입력 받음
    this.refreshExpirationTime = refreshExpirationTime * 1000;  // 설정 파일에서 seconds 단위로 입력 받음
    this.tokenPrefix = tokenPrefix == null ? "Bearer " : tokenPrefix;
    objectMapper.registerModule(new JavaTimeModule());
  }

  public Payload verifyToken(String jwtToken) {
    jwtToken = resolveToken(jwtToken);

    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    Jws<Claims> claimsJws = Jwts.parser()
//        .json(new JacksonDeserializer(Maps.of(CLAIMS_KEY, Payload.class).build()))
        .json(new JacksonDeserializer(objectMapper))
        .verifyWith(key)
        .build()
        .parseSignedClaims(jwtToken);

    return objectMapper.convertValue(claimsJws.getPayload().get(CLAIMS_KEY), Payload.class);
//    return claimsJws.getPayload().get(CLAIMS_KEY, Payload.class);
  }

  public String resolveToken(String token) {
    if (token == null) {
      throw new RuntimeException("Token is Not Empty!");
    }

    String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
    if (decodedToken.startsWith(tokenPrefix)) {
      return decodedToken.substring(tokenPrefix.length());
    }

    return decodedToken;
  }
}
