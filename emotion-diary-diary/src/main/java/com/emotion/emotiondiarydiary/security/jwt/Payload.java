package com.emotion.emotiondiarydiary.security.jwt;


import com.emotion.emotiondiarydiary.constant.SocialType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
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
}
