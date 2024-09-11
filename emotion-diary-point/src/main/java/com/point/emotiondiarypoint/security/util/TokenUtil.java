package com.point.emotiondiarypoint.security.util;

import com.point.emotiondiarypoint.security.dto.LoginAuthentication;
import com.point.emotiondiarypoint.security.jwt.Payload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class TokenUtil {

  public static String getToken() {
    LoginAuthentication authentication = (LoginAuthentication) SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new IllegalArgumentException("Authentication Token is null");
    }

    log.info("TokenUtil token = {}", authentication.getAccessToken());
    return authentication.getAccessToken();
  }

  public static Payload getPayload() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new IllegalArgumentException("Authentication Token is null");
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
