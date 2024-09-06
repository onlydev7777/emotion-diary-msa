package com.emotion.emotiondiarydiary.api.response;

import java.util.List;
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
public class DiaryListResponse {

  private List<DiaryResponse> diaryResponses;
  private MemberResponse memberResponse;
}
