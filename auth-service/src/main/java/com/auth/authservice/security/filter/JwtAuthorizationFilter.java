package com.auth.authservice.security.filter;

import com.auth.authservice.redis.RedisService;
import com.auth.authservice.security.authentication.LoginAuthentication;
import com.auth.authservice.security.jwt.JwtProvider;
import com.auth.authservice.security.jwt.Payload;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final String[] skipUrlList;
  private final RedisService redisService;
  private final JwtProvider jwtProvider;
  private final AntPathMatcher antPathMatcher = new AntPathMatcher();


  public JwtAuthorizationFilter(String[] SKIP_LIST, RedisService redisService, JwtProvider jwtProvider) {
    this.skipUrlList = SKIP_LIST;
    this.redisService = redisService;
    this.jwtProvider = jwtProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    boolean skip = Arrays.stream(skipUrlList)
        .anyMatch(url -> antPathMatcher.match(url, request.getRequestURI()));

    if (skip) {
      filterChain.doFilter(request, response);
      return;
    }

//    String token = CookieUtil.getCookieValue(request, jwtProvider.getAccessTokenHeader());
    String token = jwtProvider.resolveToken(
        request.getHeader(jwtProvider.getAccessTokenHeader())
    );

    log.info("login blacklist token = {}", token);
    if (redisService.blackListTokenGet(token)) {
//      request.getHeaderNames().asIterator().forEachRemaining(
//          h -> log.info("header = {} value = {}", h, request.getHeader(h))
//      );
      throw new JwtException("Token is blacklisted");
    }

    Payload payload = jwtProvider.verifyToken(token);

    Authentication authenticated = LoginAuthentication.authenticated(payload, List.of());
    SecurityContextHolder.getContext().setAuthentication(authenticated);

    filterChain.doFilter(request, response);
  }
}
