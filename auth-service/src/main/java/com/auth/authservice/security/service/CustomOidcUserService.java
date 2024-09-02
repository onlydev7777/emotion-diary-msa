package com.auth.authservice.security.service;

import com.auth.authservice.domain.dto.MemberDto;
import com.auth.authservice.mapper.MemberMapper;
import com.auth.authservice.security.dto.MemberDetails;
import com.auth.authservice.security.dto.oauth.OAuth2Payload;
import com.auth.authservice.security.dto.oauth.SocialMember;
import com.auth.authservice.security.jwt.Payload;
import com.auth.authservice.service.MemberService;
import com.auth.authservice.service.dto.MemberJoinRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

  private final MemberService service;
  private final MemberMapper mapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
    ClientRegistration clientRegistration = userRequest.getClientRegistration();
    OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService = new OidcUserService();
    OidcUser oidcUser = oidcUserService.loadUser(userRequest);
    SocialMember socialMember = SocialMember.of(clientRegistration, oidcUser);

    Optional<MemberDto> findMemberDto = service.findByUserIdAndSocialType(socialMember.getEmail(), socialMember.getSocialType());
    if (findMemberDto.isEmpty()) {
      MemberJoinRequest memberJoinRequest = MemberJoinRequest.of(socialMember);
      //save OAuth2 Member
      MemberDto savedDto = service.save(mapper.toDto(memberJoinRequest, passwordEncoder));
      findMemberDto = Optional.of(savedDto);
    }

    return new OAuth2Payload(socialMember, Payload.of(MemberDetails.of(findMemberDto.get())));
  }
}
