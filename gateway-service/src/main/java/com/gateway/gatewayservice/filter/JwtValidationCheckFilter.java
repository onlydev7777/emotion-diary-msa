package com.gateway.gatewayservice.filter;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Jwt 의 유효성 검증을 수행하는 필터
 * 모든 마이크로 서비스는 해당 필터를 거쳐야 한다.
 */
@Slf4j
@Component
public class JwtValidationCheckFilter extends AbstractGatewayFilterFactory<JwtValidationCheckFilter.Config> {

  private static final String TOKEN_PREFIX = "Bearer ";
  private final Environment env;

  public JwtValidationCheckFilter(Environment env) {
    super(Config.class);
    this.env = env;
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      String path = request.getURI().getPath();

      //로그인, 회원가입 요청 Valid Check Pass!
      if (path.startsWith("/auth") || path.startsWith("/oauth2") || path.startsWith("/login/oauth2")) {
        return chain.filter(exchange);
      }

      HttpHeaders headers = request.getHeaders();
      if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
        return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
      }

      String bearerToken = headers.get(HttpHeaders.AUTHORIZATION).get(0);
      String decodedToken = URLDecoder.decode(bearerToken, StandardCharsets.UTF_8);
      if (!decodedToken.startsWith(TOKEN_PREFIX)) {
        return onError(exchange, "Invalid prefix in token", HttpStatus.UNAUTHORIZED);
      }
      String jwt = decodedToken.substring(7);

      if (!isJwtValid(jwt)) {
        return onError(exchange, "JWT Token is not valid", HttpStatus.UNAUTHORIZED);
      }

      return chain.filter(exchange);
    };
  }

  private Mono<Void> onError(ServerWebExchange exchange, String error,
      HttpStatus httpStatus) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(httpStatus);

    log.error(error);
    return response.setComplete();
  }

  private String resolveToken(String bearerToken) {
    String decodedToken = URLDecoder.decode(bearerToken, StandardCharsets.UTF_8);
    if (decodedToken.startsWith(TOKEN_PREFIX)) {
      return decodedToken.substring(7);
    }
    return null;
  }

  private boolean isJwtValid(String jwt) {
    String secretKey = env.getProperty("jwt.secret-key");
    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    String subject = null;
    try {
      JwtParser parser = Jwts.parser()
          .verifyWith(key)
          .build();

      subject = parser
          .parseSignedClaims(jwt)
          .getPayload()
          .getSubject();

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return false;
    }

    if (subject == null || subject.isEmpty()) {
      return false;
    }

    return true;
  }

  public static class Config {

  }
}
