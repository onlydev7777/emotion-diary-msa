package com.auth.authservice.security.authentication;

import com.auth.authservice.security.jwt.Jwt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

  private Jwt jwt;
  private Long id;
}
