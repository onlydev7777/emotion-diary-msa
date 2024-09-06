package com.auth.authservice.security.util;

import com.auth.authservice.security.dto.oauth.OAuth2Payload;
import com.auth.authservice.security.jwt.Payload;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class TokenUtil {

  //social 로그인 최초 요청
  public static boolean isInitSocialLogin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof OAuth2AuthenticationToken) {
      return true;
    }
    return false;
  }

  public static Payload getPayload() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new IllegalArgumentException("Authentication Token is null");
    }

    if (isInitSocialLogin()) {
      OAuth2Payload oAuth2Payload = (OAuth2Payload) authentication.getPrincipal();
      return oAuth2Payload.getPayload();
    }

    return (Payload) authentication.getPrincipal();
  }

  public static Long getMemberId() {
    return getPayload().getId();
  }

  public static String getUserId() {
    return getPayload().getUserId();
  }

  public static void idCheck(Long memberId) {
    if (getMemberId() != memberId) {
      throw new AccessDeniedException("memberId is Not Equals");
    }
  }
}
