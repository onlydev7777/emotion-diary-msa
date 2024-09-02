package com.auth.authservice.api;

import com.auth.authservice.domain.Member;
import com.auth.authservice.domain.dto.MemberDto;
import com.auth.authservice.error.ApiResult;
import com.auth.authservice.mapper.MemberMapper;
import com.auth.authservice.redis.RedisService;
import com.auth.authservice.repository.MemberRepository;
import com.auth.authservice.security.authentication.LoginAuthentication;
import com.auth.authservice.security.authentication.handler.LoginSuccessHandler;
import com.auth.authservice.security.dto.MemberDetails;
import com.auth.authservice.security.jwt.JwtProvider;
import com.auth.authservice.security.jwt.Payload;
import com.auth.authservice.service.MemberService;
import com.auth.authservice.service.dto.MemberJoinRequest;
import com.auth.authservice.service.dto.MemberJoinResponse;
import com.auth.authservice.util.CookieUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

  private final JwtProvider jwtProvider;
  private final RedisService redisService;

  private final MemberService memberService;
  private final MemberRepository memberRepository;
  private final MemberMapper mapper;

  private final PasswordEncoder passwordEncoder;

  @GetMapping("/logout")
  public ResponseEntity<ApiResult<String>> logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
    if (authentication != null) {
      new SecurityContextLogoutHandler().logout(request, response, authentication);
      String accessToken = jwtProvider.resolveToken(request.getHeader(jwtProvider.getAccessTokenHeader()));
//      String accessToken = CookieUtil.getCookieValue(request, jwtProvider.getAccessTokenHeader());
      redisService.blackListTokenSave(accessToken, Boolean.TRUE);
      CookieUtil.deleteCookies(request, response, jwtProvider.getAccessTokenHeader(), jwtProvider.getRefreshTokenHeader(), "id");
    }
    return ResponseEntity.ok(ApiResult.OK("logout"));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    //1. validate refresh token
//    String refreshToken = request.getHeader(jwtProvider.getRefreshTokenHeader());
    String refreshToken = CookieUtil.getCookieValue(request, jwtProvider.getRefreshTokenHeader());
    String requestRefreshToken = jwtProvider.resolveToken(refreshToken);
    String redisKey = jwtProvider.verifyRefreshToken(requestRefreshToken);
    String findRefreshToken = redisService.refreshTokenGet(redisKey);
    if (!requestRefreshToken.equals(findRefreshToken)) {
      throw new JwtException("Invalid refresh token");
    }

    //2. find Member
    Long memberId = Long.parseLong(redisKey.split("/")[0]);   //get MemberId
    Member member = memberRepository.findById(memberId)
        .orElseThrow();

    //3. as-is access token to blacklist token
//    String accessToken = request.getHeader(jwtProvider.getAccessTokenHeader());
//    String accessToken = CookieUtil.getCookieValue(request, jwtProvider.getAccessTokenHeader());
//    String requestAccessToken = jwtProvider.resolveToken(accessToken);
//    redisService.blackListTokenSave(requestAccessToken, true);

    //4. delete cookie
//    CookieUtil.deleteCookies(request, response, jwtProvider.getAccessTokenHeader(), jwtProvider.getRefreshTokenHeader(), "id");

    //5. create payload
    Payload payload = Payload.of(MemberDetails.of(member));

    //6. set new token and refresh token to response header
    LoginAuthentication refreshAuthentication = LoginAuthentication.authenticated(payload, List.of());
    SecurityContextHolder.getContext().setAuthentication(refreshAuthentication);
    new LoginSuccessHandler(redisService, jwtProvider).onAuthenticationSuccess(request, response, refreshAuthentication);
  }

  @GetMapping(value = "/test-ok")
  public ResponseEntity<ApiResult<Payload>> testOk() {
    LoginAuthentication authentication = (LoginAuthentication) SecurityContextHolder.getContext().getAuthentication();
    Payload principal = (Payload) authentication.getPrincipal();
    return ResponseEntity.ok(ApiResult.OK(principal));
  }

  @PostMapping("/signup")
  public ResponseEntity<ApiResult<MemberJoinResponse>> joinMember(@RequestBody MemberJoinRequest request) {
    MemberDto savedMemberDto = memberService.save(mapper.toDto(request, passwordEncoder));
    return ResponseEntity.ok(ApiResult.OK(mapper.toResponse(savedMemberDto)));
  }
}
