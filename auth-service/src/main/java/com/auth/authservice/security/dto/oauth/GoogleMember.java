package com.auth.authservice.security.dto.oauth;

import com.auth.authservice.constant.SocialType;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleMember extends SocialMember {

  public GoogleMember(SocialType socialType, OAuth2User oAuth2User) {
    super(socialType, oAuth2User);
  }

  @Override
  public String getOAuthKey() {
    return oAuth2User.getAttribute("sub");
  }

  @Override
  public String getUsername() {
    return oAuth2User.getAttribute("name");
  }
}
