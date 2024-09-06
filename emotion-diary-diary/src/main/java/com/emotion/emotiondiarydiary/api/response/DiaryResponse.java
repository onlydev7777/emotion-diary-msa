package com.emotion.emotiondiarydiary.api.response;

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
public class DiaryResponse {

  private Long id;
  private String subject;
  private String content;
  private EmotionStatus emotionStatus;
  private LocalDate diaryDate;
  private String diaryYearMonth;
  private MemberResponse memberResponse;
}
