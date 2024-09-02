package com.auth.authservice.security.jwt;


import com.auth.authservice.constant.SocialType;
import com.auth.authservice.domain.embeddable.Email;
import com.auth.authservice.security.dto.MemberDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Payload {

  private Long id;
  private String userId;
  private Email email;
  private SocialType socialType;
//  private Role role;

  public String getRedisKey() {
    return this.id + "/" + this.userId + "/" + this.email.getEmail() + "/" + this.socialType;
  }

  public static Payload of(MemberDetails memberDetails) {
    return new Payload(memberDetails.getId(), memberDetails.getUserId(), memberDetails.getEmail(), memberDetails.getSocialType());
  }
}
