package com.auth.authservice.security.authentication;

import com.auth.authservice.security.jwt.Payload;
import java.util.Collection;
import java.util.List;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;

public class LoginAuthentication extends AbstractAuthenticationToken {

  private Object principal;
  private Object credentials;

  public LoginAuthentication() {
    super(null);
  }

  public LoginAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.credentials = credentials;
    setAuthenticated(true);
  }

  public LoginAuthentication(Object principal, Object credentials) {
    super(null);
    this.principal = principal;
    this.credentials = credentials;
    setAuthenticated(false);
  }

  public static LoginAuthentication unauthenticated(LoginRequest request) {
    String principal = request.getUserId() + "^" + request.getSocialType().name();
    String credentials = request.getPassword();

    validateCheck(principal, credentials);
    return new LoginAuthentication(principal, credentials);
  }

  public static LoginAuthentication authenticated(Payload payload, List<? extends GrantedAuthority> authorities) {
    return new LoginAuthentication(payload, null, authorities);
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
