package com.emotion.emotiondiarydiary.security;

import com.emotion.emotiondiarydiary.security.filter.JwtAuthorizationFilter;
import com.emotion.emotiondiarydiary.security.filter.ReceivingJwtExceptionFilter;
import com.emotion.emotiondiarydiary.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

  private final JwtProvider jwtProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(request -> request
            .anyRequest().authenticated())
        .addFilterBefore(jwtAuthorizationFilter(), AuthorizationFilter.class)
        .addFilterBefore(receivingJwtExceptionFilter(), JwtAuthorizationFilter.class);

    SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

    return http.build();
  }

  private ReceivingJwtExceptionFilter receivingJwtExceptionFilter() {
    return new ReceivingJwtExceptionFilter();
  }

  public JwtAuthorizationFilter jwtAuthorizationFilter() {
    JwtAuthorizationFilter jwtAuthenticationFilter = new JwtAuthorizationFilter(jwtProvider);
    return jwtAuthenticationFilter;
  }
}
