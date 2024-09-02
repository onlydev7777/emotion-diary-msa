package com.auth.authservice.config;

import com.auth.authservice.security.jwt.JwtProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
public class CorsConfig {

  private final JwtProvider jwtProvider;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("http://localhost:8081");//localhost:8081 에서의 요청 허용
    configuration.addAllowedHeader("*");//Request Header 전체 허용
    configuration.addAllowedMethod("*");//HTTP 메서드 전체 허용
    configuration.setAllowCredentials(true);//쿠키 전송 허용
    configuration.setMaxAge(3600L);//Preflight Request 캐싱 시간 : 1시간
    configuration.setExposedHeaders(List.of(jwtProvider.getAccessTokenHeader(), jwtProvider.getRefreshTokenHeader()));//Response Header 허용

    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);  // 모든 url 패턴 허용
    return urlBasedCorsConfigurationSource;
  }
}
