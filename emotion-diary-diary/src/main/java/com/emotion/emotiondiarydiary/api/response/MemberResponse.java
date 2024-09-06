package com.emotion.emotiondiarydiary.api.response;

import com.emotion.emotiondiarydiary.constant.SocialType;
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
public class MemberResponse {

  private Long id;
  private String userId;
  private String name;
  private String email;
  private SocialType socialType;
}
