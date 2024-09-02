package com.auth.authservice.security.filter;

import com.auth.authservice.security.authentication.LoginAuthentication;
import com.auth.authservice.security.authentication.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class LoginRequestFilter extends AbstractAuthenticationProcessingFilter {

  public LoginRequestFilter(String defaultFilterProcessesUrl) {
    super(defaultFilterProcessesUrl);
  }

  /*
   * '/login' 으로 들어오는 요청일 경우
   *
   * 1. LoginRequest 로 파싱
   * 2. LoginAuthentication(미인증 상태) 으로 변환
   * 3. Authentication 에게 인증 위임 : AuthenticationManager(ProviderManager) >> Provider(LoginAuthenticationProvider) 에서 인증 시도
   *
   */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {

    LoginRequest loginRequest = new ObjectMapper().readValue(request.getReader(), LoginRequest.class);
    LoginAuthentication unauthenticated = LoginAuthentication.unauthenticated(loginRequest);

    return this.getAuthenticationManager().authenticate(unauthenticated);
  }
}
