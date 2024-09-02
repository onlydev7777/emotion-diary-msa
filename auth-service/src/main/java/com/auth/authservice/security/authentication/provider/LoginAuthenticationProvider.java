package com.auth.authservice.security.authentication.provider;

import com.auth.authservice.security.authentication.LoginAuthentication;
import com.auth.authservice.security.dto.MemberDetails;
import com.auth.authservice.security.jwt.Payload;
import com.auth.authservice.security.service.LoginService;
import com.auth.authservice.security.util.AuthenticationUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

  private final LoginService service;
  private final PasswordEncoder passwordEncoder;

  /*
   * 1. 미인증 상태의 Authentication 을 파라미터로 받는다.
   * 2. DB 에서 요청 id와 요청 password를 검증
   * 3. 검증 완료 시 '인증완료' 상태의 Authrntication 을 반환한다.
   * 4. 반환된 Authrntication 은 AbstractAuthenticationProcessingFilter 의 successfulAuthentication 메서드 에서 SecurityContext 에 저장된다.
   */
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String idAndSocialType = AuthenticationUtil.toIdAndSocialType(authentication.getPrincipal());
    MemberDetails memberDetails = (MemberDetails) service.loadUserByUsername(idAndSocialType);

    String credentials = (String) authentication.getCredentials();

    if (!passwordEncoder.matches(credentials, memberDetails.getPassword())) {
      throw new BadCredentialsException("Password Not Matches!");
    }

    return LoginAuthentication.authenticated(Payload.of(memberDetails), List.of());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return LoginAuthentication.class.isAssignableFrom(authentication);
  }
}
