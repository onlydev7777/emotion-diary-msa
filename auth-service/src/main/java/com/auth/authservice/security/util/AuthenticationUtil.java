package com.auth.authservice.security.util;

public class AuthenticationUtil {

  public static String toIdAndSocialType(Object principal) {
    if (principal instanceof String idAndSocialType) {
      return idAndSocialType;
    }
    throw new IllegalArgumentException("Principal Is Not String");
  }
}
