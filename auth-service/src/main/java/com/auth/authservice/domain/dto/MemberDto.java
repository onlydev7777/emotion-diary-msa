package com.auth.authservice.domain.dto;

import com.auth.authservice.constant.SocialType;
import com.auth.authservice.domain.embeddable.Email;
import com.auth.authservice.domain.embeddable.Password;
import java.util.Set;
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
public class MemberDto {

  private Long id;
  private String userId;
  private Password password;
  private String name;
  private Email email;
  private SocialType socialType;
  private Set<Long> roleIds;

  public void setRoleIds(Set<Long> roleIds) {
    this.roleIds = roleIds;
  }
}
