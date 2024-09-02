package com.auth.authservice.service.dto;

import com.auth.authservice.constant.SocialType;
import com.auth.authservice.domain.embeddable.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinResponse {

  private Long id;
  private String userId;
  private String name;
  private Email email;
  private SocialType socialType;
}
