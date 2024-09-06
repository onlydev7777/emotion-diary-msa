package com.auth.authservice.api;

import com.auth.authservice.domain.dto.MemberDto;
import com.auth.authservice.error.ApiResult;
import com.auth.authservice.mapper.MemberMapper;
import com.auth.authservice.security.authentication.LoginAuthentication;
import com.auth.authservice.security.jwt.Payload;
import com.auth.authservice.service.MemberService;
import com.auth.authservice.service.dto.MemberJoinRequest;
import com.auth.authservice.service.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

  private final MemberService memberService;
  private final MemberMapper mapper;

  private final PasswordEncoder passwordEncoder;

  @GetMapping(value = "/test-ok")
  public ResponseEntity<ApiResult<Payload>> testOk() {
    LoginAuthentication authentication = (LoginAuthentication) SecurityContextHolder.getContext().getAuthentication();
    Payload principal = (Payload) authentication.getPrincipal();
    return ResponseEntity.ok(ApiResult.OK(principal));
  }

  @PostMapping("/signup")
  public ResponseEntity<ApiResult<MemberResponse>> joinMember(@RequestBody MemberJoinRequest request) {
    MemberDto savedMemberDto = memberService.save(mapper.toDto(request, passwordEncoder));
    return ResponseEntity.ok(ApiResult.OK(mapper.toResponse(savedMemberDto)));
  }
}
