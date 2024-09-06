package com.emotion.emotiondiarydiary.dto;

import com.emotion.emotiondiarydiary.constant.EmotionStatus;
import java.time.LocalDate;
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
public class DiaryDto {

  private Long id;
  private String subject;
  private String content;
  private EmotionStatus emotionStatus;
  private LocalDate diaryDate;
  private String diaryYearMonth;
  private Long memberId;

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }
}
