package com.auth.authservice.security.authentication;

import com.auth.authservice.constant.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

  private String userId;
  private String password;
  private SocialType socialType;
}
