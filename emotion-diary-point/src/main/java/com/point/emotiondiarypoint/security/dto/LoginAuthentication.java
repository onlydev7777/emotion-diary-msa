package com.point.emotiondiarypoint.security.dto;

import com.point.emotiondiarypoint.security.jwt.Payload;
import java.util.Collection;
import java.util.List;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;

public class LoginAuthentication extends AbstractAuthenticationToken {

  private String accessToken;
  private Object principal;
  private Object credentials;

  public LoginAuthentication() {
    super(null);
  }

  public LoginAuthentication(String accessToken, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.accessToken = accessToken;
    this.principal = principal;
    this.credentials = credentials;
    setAuthenticated(true);
  }

  public static LoginAuthentication authenticated(String accessToken, Payload payload, List<? extends GrantedAuthority> authorities) {
    return new LoginAuthentication(accessToken, payload, null, authorities);
  }

  public String getAccessToken() {
    return accessToken;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  @Override
  public Object getCredentials() {
    return credentials;
  }

  private static void validateCheck(String principal, String credentials) {
    if (principal == null) {
      throw new BadCredentialsException("ID는 필수입니다.");
    }
    if (credentials == null) {
      throw new BadCredentialsException("Password는 필수입니다.");
    }
  }
}
