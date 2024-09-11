package com.auth.authservice.security.jwt;


import com.auth.authservice.constant.SocialType;
import com.auth.authservice.security.dto.MemberDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Payload {

  private Long id;
  private String userId;
  private String email;
  private SocialType socialType;
  private LocalDateTime loginTime;
//  private Role role;

  public String getRedisKey() {
    return this.id + "/" + this.userId + "/" + this.email + "/" + this.socialType + "/" + loginTime.toString();
  }

  public static Payload of(MemberDetails memberDetails) {
    return new Payload(memberDetails.getId(), memberDetails.getUserId(), memberDetails.getEmail().getEmail(), memberDetails.getSocialType(),
        LocalDateTime.now());
  }
}
