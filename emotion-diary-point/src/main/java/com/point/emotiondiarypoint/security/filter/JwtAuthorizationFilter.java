package com.point.emotiondiarypoint.security.filter;

import com.point.emotiondiarypoint.security.dto.LoginAuthentication;
import com.point.emotiondiarypoint.security.jwt.JwtProvider;
import com.point.emotiondiarypoint.security.jwt.Payload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;


  public JwtAuthorizationFilter(JwtProvider jwtProvider) {
    this.jwtProvider = jwtProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = jwtProvider.resolveToken(
        request.getHeader(jwtProvider.getAccessTokenHeader())
    );

    log.info("Diary Request Token = {}", token);
    Payload payload = jwtProvider.verifyToken(token);

    Authentication authenticated = LoginAuthentication.authenticated(token, payload, List.of());
    SecurityContextHolder.getContext().setAuthentication(authenticated);

    filterChain.doFilter(request, response);
  }
}
