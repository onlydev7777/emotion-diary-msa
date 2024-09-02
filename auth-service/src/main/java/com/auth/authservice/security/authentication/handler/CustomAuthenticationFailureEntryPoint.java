package com.auth.authservice.security.authentication.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFailureEntryPoint implements AuthenticationEntryPoint {


  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    PrintWriter writer = response.getWriter();
    writer.write(authException.getMessage());
    writer.flush();
    writer.close();
  }
}
